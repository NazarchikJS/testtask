package com.nazartaranyuk.domain.usecases

import com.nazartaranyuk.domain.repositories.FirebaseLinkRepository
import kotlinx.coroutines.flow.Flow

class LoadLinkUseCase(
    private val repository: FirebaseLinkRepository
) {
    suspend operator fun invoke() : Flow<String> {
        return repository.loadLink()
    }
}