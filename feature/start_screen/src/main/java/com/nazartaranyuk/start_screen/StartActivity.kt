package com.nazartaranyuk.start_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.nazartaranyuk.game_start_screen.GameActivity
import com.nazartaranyuk.start_screen.databinding.ActivityStartBinding
import com.nazartaranyuk.start_screen.intentions.Intentions
import com.nazartaranyuk.start_screen.states.State
import com.nazartaranyuk.web_screen.WebViewActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartActivity : AppCompatActivity() {

    private var binding: ActivityStartBinding? = null

    private val remoteConfig by inject<FirebaseRemoteConfig>()

    private val viewModel by viewModel<StartActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        binding?.let { binding ->
            setContentView(binding.root)
            val configSettings = remoteConfigSettings {
                minimumFetchIntervalInSeconds = 3600
            }
            remoteConfig.setConfigSettingsAsync(configSettings)
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.sendIntent(Intentions.CheckGamePassIntent)
                viewModel.receiveState()
                viewModel.gamePassSharedFlow.collect { state ->
                    when (state) {
                        is State.GamePassState -> {
                            checkGamePass(state.isGamePassEnabled)
                        }
                    }
                }
            }
        }
    }

    private fun checkGamePass(isGamePassEnabled: Boolean) {
        Log.d(GAME_PASS_TAG, "Game pass is: $isGamePassEnabled")
        when (isGamePassEnabled) {
            true -> {
                startActivity(Intent(this, GameActivity::class.java))
                finish()
            }
            false -> {
                startActivity(Intent(this, WebViewActivity::class.java))
                finish()
            }
        }
    }

    companion object {
        const val GAME_PASS_TAG = "GamePassKey"
    }
}