package com.nazartaranyuk.data.data

import androidx.room.*
import com.nazartaranyuk.data.models.Coins
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCoins(coins: Coins)

    @Query("SELECT * FROM coins ORDER BY id DESC LIMIT 1")
    fun getCoinsBalance() : Flow<Coins>

    @Query("SELECT COUNT(*) FROM coins")
    fun checkIsEmpty() : Int
}