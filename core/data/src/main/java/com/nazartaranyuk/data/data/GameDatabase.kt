package com.nazartaranyuk.data.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nazartaranyuk.data.models.LastWinEntity

@Database(entities = [LastWinEntity::class], version = 1, exportSchema = false)
abstract class GameDatabase : RoomDatabase() {
    abstract fun getDatabaseDAO(): LastWinDAO

}