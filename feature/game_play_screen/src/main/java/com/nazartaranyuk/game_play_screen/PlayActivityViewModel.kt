package com.nazartaranyuk.game_play_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nazartaranyuk.data.data.CoinsRepository
import com.nazartaranyuk.data.models.LastWinEntity
import com.nazartaranyuk.data.data.GameRepository
import com.nazartaranyuk.data.data.ItemProvider
import com.nazartaranyuk.data.models.Coins
import com.nazartaranyuk.game_play_screen.intentions.Intentions
import com.nazartaranyuk.game_play_screen.states.States
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.lang.Thread.State
import kotlin.random.Random

class PlayActivityViewModel(
    private val repository: GameRepository,
    private val coinsRepository: CoinsRepository
) : ViewModel() {

    private val itemsProvider = ItemProvider()

    private val _stateFlow = MutableStateFlow<States>(States.EmptyState)
    val stateFlow: StateFlow<States> get() = _stateFlow

    init {
        initEmptyWin()
    }

    fun sendIntent(intent: Intentions) {
        when (intent) {
            is Intentions.UpdateCoinsBalance -> {
                updateCoinsBalance()
            }
            is Intentions.DecreaseCoinsBalance -> {
                decreaseCoinsBalance()
            }
            is Intentions.UpdateListIntent -> {
                setList()
            }
            is Intentions.UpdateLastWinIntent -> {
                getLastWin()
            }
            is Intentions.SetWinIntent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if (coinsRepository.getCoinsBalance().first().coins == 0) {
                        _stateFlow.emit(States.ErrorState)
                    } else {
                        setWin()
                    }
                }
            }
        }
    }

    private fun decreaseCoinsBalance() {
        viewModelScope.launch(Dispatchers.IO) {
            // getting current balance
            val currentBalance = coinsRepository.getCoinsBalance().first().coins
            // decrease 5 from current balance
            var updatedBalance = 0
            if (currentBalance >= 5) {
                updatedBalance = currentBalance - 5
            }
            coinsRepository.insertCoins(Coins(coins = updatedBalance))
        }
    }

    private fun updateCoinsBalance() {
        viewModelScope.launch(Dispatchers.IO) {
            val balance = coinsRepository.getCoinsBalance().first().coins
            Log.d("ViewModelCoins", balance.toString())
            _stateFlow.emit(States.UpdatedCoinsState(balance))
        }
    }

    private fun setList() = viewModelScope.launch(Dispatchers.IO) {
        _stateFlow.emit(States.UpdatedListState(itemsProvider.provideItems()))
    }

    private fun initEmptyWin() {
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.checkIsEmpty() == 0) {
                repository.insertLastWin(
                    LastWinEntity(
                        lastWin = 0
                    )
                )
            }
        }
    }

    private fun getLastWin() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getLastWin().collect { lastWin ->
                _stateFlow.emit(States.UpdatedLastWinState(lastWin.lastWin))
            }

        }
    }

    private fun setWin() {
        val win = Random.nextInt(1000)
        _stateFlow.tryEmit(States.UpdatedLastWinState(win))
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateLastWin(
                LastWinEntity(
                    lastWin = win
                )
            )
        }
    }
}