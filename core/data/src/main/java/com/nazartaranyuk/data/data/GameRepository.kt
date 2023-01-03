package com.nazartaranyuk.data.data

import com.nazartaranyuk.data.models.LastWinEntity
import kotlinx.coroutines.flow.Flow

interface GameRepository {

    fun updateLastWin(lastWinEntity: LastWinEntity)

    fun insertLastWin(lastWinEntity: LastWinEntity)

    fun getLastWin() : Flow<LastWinEntity>

    fun checkIsEmpty() : Int
}