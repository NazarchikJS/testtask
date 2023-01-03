package com.nazartaranyuk.domain.usecases

import com.nazartaranyuk.domain.repositories.FirebaseGamePassRepository
import kotlinx.coroutines.flow.Flow

class LoadGamePassUseCase(
    private val repository: FirebaseGamePassRepository
) {

    suspend operator fun invoke() : Flow<Boolean> {
        return repository.checkGamePass()
    }
}