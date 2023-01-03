package com.nazartaranyuk.game_shop_screen.states

sealed class States {
    object EmptyState : States()
    data class UpdatedCoinsBalance(val coins: Int) : States()
}
