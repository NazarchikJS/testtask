package com.nazartaranyuk.domain.repositories

import kotlinx.coroutines.flow.Flow

interface FirebaseLinkRepository {

    suspend fun loadLink() : Flow<String>
}