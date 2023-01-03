package com.nazartaranyuk.domain.repositories

import kotlinx.coroutines.flow.Flow

interface FirebaseGamePassRepository {

    // Repository has to return boolean value of game pass parameter
    suspend fun checkGamePass() : Flow<Boolean>
}