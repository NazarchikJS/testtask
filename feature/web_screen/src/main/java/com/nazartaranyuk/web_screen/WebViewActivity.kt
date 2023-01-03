package com.nazartaranyuk.web_screen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.KeyEvent
import android.webkit.*
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nazartaranyuk.web_screen.databinding.ActivityWebViewBinding
import com.nazartaranyuk.web_screen.intentions.Intentions
import com.nazartaranyuk.web_screen.states.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class WebViewActivity : AppCompatActivity() {

    private var binding: ActivityWebViewBinding? = null
    private var messageAb: ValueCallback<Array<Uri?>>? = null

    private val firebase by lazy {
        Firebase.database
    }
    private val databaseReference by lazy {
        firebase.getReference("link")
    }

    private val viewModel by viewModel<WebViewActivityViewModel>()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        binding?.let { binding ->
            setContentView(binding.root)
            databaseReference.setValue("Heelooo")
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.sendIntent(Intentions.LoadLinkIntent)
                viewModel.webLinkSharedFlow.collect { state ->
                    when (state) {
                        is State.LoadedWebLinkState -> {
                            Log.d("WebViewLink", state.link)
                            binding.webView.loadUrl(state.link)
                        }
                    }
                }
            }
            binding.webView.webViewClient = LocalClient()
            binding.webView.settings.userAgentString = System.getProperty("http.agent")
            binding.webView.settings.javaScriptEnabled = true
            binding.webView.settings.domStorageEnabled = true
            CookieManager.getInstance().setAcceptCookie(true)
            CookieManager.getInstance().setAcceptThirdPartyCookies(binding.webView, true)
            binding.webView.webChromeClient = object : WebChromeClient() {

                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                }

                override fun onShowFileChooser(
                    webView: WebView?,
                    filePathCallback: ValueCallback<Array<Uri?>>?,
                    fileChooserParams: FileChooserParams?
                ): Boolean {
                    messageAb = filePathCallback
                    selectImage()
                    return true
                }

                @RequiresApi(Build.VERSION_CODES.O)
                override fun onCreateWindow(
                    view: WebView?,
                    isDialog: Boolean,
                    isUserGesture: Boolean,
                    resultMsg: Message?
                ): Boolean {
                    val anotherWebView = WebView(this@WebViewActivity)
                    anotherWebView.webChromeClient = this
                    anotherWebView.settings.domStorageEnabled = true
                    anotherWebView.settings.javaScriptCanOpenWindowsAutomatically = true
                    anotherWebView.settings.javaScriptEnabled = true
                    anotherWebView.settings.setSupportMultipleWindows(true)
                    val transport = resultMsg?.obj as WebView.WebViewTransport
                    transport.webView = anotherWebView
                    resultMsg.sendToTarget()
                    anotherWebView.webViewClient = object : WebViewClient() {

                        override fun onReceivedSslError(
                            view: WebView?,
                            handler: SslErrorHandler?,
                            error: SslError?
                        ) {
                            handler?.proceed()
                        }

                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            url: String?
                        ): Boolean {
                            view!!.loadUrl(url!!)
                            return true
                        }
                    }
                    return true
                }

            }

        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.getAction() === KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (binding?.webView?.canGoBack() == true) {
                        binding?.webView?.goBack()
                    }
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = INTENT_TYPE
        startActivityForResult(
            Intent.createChooser(intent, CHOOSER_TITLE), RESULT_CODE
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (binding?.webView?.canGoBack() == true) {
            binding?.webView?.goBack()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_CANCELED -> {
                messageAb?.onReceiveValue(null)
                return
            }
            Activity.RESULT_OK -> {
                if (messageAb == null) return

                messageAb!!.onReceiveValue(
                    WebChromeClient.FileChooserParams.parseResult(
                        resultCode,
                        data
                    )
                )
                messageAb = null
            }
        }

        onBackPressedDispatcher.addCallback(this@WebViewActivity,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding?.webView?.canGoBack() == true) {
                        binding?.webView?.goBack()
                    }
                }
            }
        )
    }

    private inner class LocalClient : WebViewClient() {
        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            super.onReceivedError(view, request, error)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            CookieManager.getInstance().flush()
            databaseReference.setValue(url.toString())

        }

        @SuppressLint("WebViewClientOnReceivedSslError")
        override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler?,
            error: SslError?
        ) {
            handler?.proceed()
        }
    }

    companion object {
        const val INTENT_TYPE = "image/*"
        const val CHOOSER_TITLE = "Image Chooser"
        const val RESULT_CODE = 1
    }
}