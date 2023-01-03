package com.nazartaranyuk.start_screen.states

sealed class State {
    data class GamePassState(
        val isGamePassEnabled: Boolean
    ) : State()
}
