package com.nazartaranyuk.data.data

import androidx.room.*
import com.nazartaranyuk.data.models.LastWinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LastWinDAO {

    @Update
    fun updateLastWin(lastWinEntity: LastWinEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLastWin(lastWinEntity: LastWinEntity)

    @Query("SELECT * FROM winhistory ORDER BY id DESC LIMIT 1")
    fun getLastWin() : Flow<LastWinEntity>

    @Query("SELECT COUNT(*) FROM winhistory")
    fun checkIsEmpty() : Int
}