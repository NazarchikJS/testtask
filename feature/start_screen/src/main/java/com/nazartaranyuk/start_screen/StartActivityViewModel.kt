package com.nazartaranyuk.start_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazartaranyuk.domain.usecases.LoadGamePassUseCase
import com.nazartaranyuk.start_screen.intentions.Intentions
import com.nazartaranyuk.start_screen.states.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class StartActivityViewModel(
    private val loadGamePassUseCase: LoadGamePassUseCase
) : ViewModel() {

    private val _gamePassSharedFlow = MutableSharedFlow<State>()
    val gamePassSharedFlow: SharedFlow<State> get() = _gamePassSharedFlow

    // Value by default
    private var gamePassState: Boolean = false

    suspend fun sendIntent(intent: Intentions) {
        when(intent) {
            is Intentions.CheckGamePassIntent -> {
                loadGamePassUseCase().collect { isGamePassEnabled ->
                    gamePassState = isGamePassEnabled
                }
                Log.d(VIEW_MODEL_TAG, gamePassState.toString())
            }
        }
    }

    fun receiveState() {
        viewModelScope.launch(Dispatchers.IO) {
            _gamePassSharedFlow.emit(State.GamePassState(gamePassState))
        }
    }

    companion object {
        const val VIEW_MODEL_TAG = "ViewModelTag"
    }
}