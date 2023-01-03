package com.nazartaranyuk.data.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nazartaranyuk.data.models.Coins
import com.nazartaranyuk.data.models.LastWinEntity

@Database(entities = [Coins::class], version = 1, exportSchema = false)
abstract class CoinsDatabase : RoomDatabase() {

    abstract fun getDatabaseDAO(): CoinsDAO
}