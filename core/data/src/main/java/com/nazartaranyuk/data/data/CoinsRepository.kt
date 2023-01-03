package com.nazartaranyuk.data.data

import com.nazartaranyuk.data.models.Coins
import kotlinx.coroutines.flow.Flow

interface CoinsRepository {

    fun getCoinsBalance() : Flow<Coins>

    fun insertCoins(coins: Coins)

    fun checkIsEmpty() : Int
}