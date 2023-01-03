package com.nazartaranyuk.game_shop_screen.intentions

sealed class Intentions {
    data class BuyCoinsIntent(val coins: Int) : Intentions()
    object UpdateCoinsBalanceIntent : Intentions()
}
