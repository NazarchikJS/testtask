package com.nazartaranyuk.game_play_screen.states

import com.nazartaranyuk.data.models.Item

sealed class States {
    object EmptyState : States()
    data class UpdatedListState(val list: List<Item>) : States()
    data class UpdatedLastWinState(val win: Int) : States()
    data class UpdatedCoinsState(val coins: Int) : States()
    object ErrorState : States()
}
