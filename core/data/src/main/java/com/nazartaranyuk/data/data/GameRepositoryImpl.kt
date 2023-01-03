package com.nazartaranyuk.data.data

import com.nazartaranyuk.data.models.LastWinEntity
import kotlinx.coroutines.flow.Flow

class GameRepositoryImpl(private val dao: LastWinDAO) : GameRepository {

    override fun updateLastWin(lastWinEntity: LastWinEntity) {
        dao.updateLastWin(lastWinEntity)
    }

    override fun insertLastWin(lastWinEntity: LastWinEntity) {
        return dao.insertLastWin(lastWinEntity)
    }

    override fun getLastWin(): Flow<LastWinEntity> {
        return dao.getLastWin()
    }

    override fun checkIsEmpty(): Int {
        return dao.checkIsEmpty()
    }
}