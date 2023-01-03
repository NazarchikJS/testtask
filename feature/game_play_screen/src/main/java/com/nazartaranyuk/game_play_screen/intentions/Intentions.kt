package com.nazartaranyuk.game_play_screen.intentions


sealed class Intentions {
    object UpdateCoinsBalance : Intentions()
    object UpdateListIntent : Intentions()
    object UpdateLastWinIntent : Intentions()
    object SetWinIntent : Intentions()
    object DecreaseCoinsBalance : Intentions()
}
