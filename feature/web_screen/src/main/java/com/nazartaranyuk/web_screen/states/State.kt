package com.nazartaranyuk.web_screen.states

sealed class State {
    data class LoadedWebLinkState(val link: String) : State()
}
