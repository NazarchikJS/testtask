package com.nazartaranyuk.web_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazartaranyuk.domain.usecases.LoadLinkUseCase
import com.nazartaranyuk.web_screen.intentions.Intentions
import com.nazartaranyuk.web_screen.states.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WebViewActivityViewModel(
    private val loadLinkUseCase: LoadLinkUseCase
) : ViewModel() {

    private val _webLinkSharedFlow = MutableSharedFlow<State>()
    val webLinkSharedFlow: SharedFlow<State> get() = _webLinkSharedFlow


    suspend fun sendIntent(intent: Intentions) {
        when(intent) {
            is Intentions.LoadLinkIntent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    loadLinkUseCase().collect { link ->
                        _webLinkSharedFlow.emit(State.LoadedWebLinkState(link))
                    }
                }
            }
        }
    }
}