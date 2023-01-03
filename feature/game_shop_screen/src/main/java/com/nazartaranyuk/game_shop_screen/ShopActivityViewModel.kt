package com.nazartaranyuk.game_shop_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazartaranyuk.data.data.CoinsRepository
import com.nazartaranyuk.data.data.CoinsRepositoryImpl
import com.nazartaranyuk.data.models.Coins
import com.nazartaranyuk.game_shop_screen.intentions.Intentions
import com.nazartaranyuk.game_shop_screen.states.States
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ShopActivityViewModel(
    private val repository: CoinsRepository
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<States>(States.EmptyState)
    val stateFlow: StateFlow<States> get() = _stateFlow

    init {
        initEmptyCoinsBalance()
    }

    private fun initEmptyCoinsBalance() {
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.checkIsEmpty() == 0) { // repository is empty, we need to put there init value
                repository.insertCoins(Coins(coins = 0))
            }
        }
    }

    fun sendIntent(intent: Intentions) {
        when(intent) {
            is Intentions.BuyCoinsIntent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    // getting current balance
                    val currentBalance = repository.getCoinsBalance().first().coins
                    val updatedBalance = intent.coins + currentBalance
                    repository.insertCoins(Coins(coins = updatedBalance))
                    _stateFlow.emit(States.UpdatedCoinsBalance(updatedBalance))
                }
            }
            is Intentions.UpdateCoinsBalanceIntent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val balance = repository.getCoinsBalance().first().coins
                    _stateFlow.emit(States.UpdatedCoinsBalance(balance))
                }
            }
        }
    }
}