package com.nazartaranyuk.data.data

import com.nazartaranyuk.data.models.Coins
import kotlinx.coroutines.flow.Flow

class CoinsRepositoryImpl(private val dao: CoinsDAO) : CoinsRepository {

    override fun getCoinsBalance(): Flow<Coins> {
        return dao.getCoinsBalance()
    }

    override fun insertCoins(coins: Coins) {
        dao.insertCoins(coins)
    }

    override fun checkIsEmpty(): Int {
        return dao.checkIsEmpty()
    }
}