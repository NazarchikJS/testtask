package com.nazartaranyuk.data

import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.nazartaranyuk.domain.repositories.FirebaseGamePassRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class CheckGamePassRepositoryImpl(
    private val remoteConfig: FirebaseRemoteConfig,
) : FirebaseGamePassRepository {

    override suspend fun checkGamePass() = flow<Boolean> {
        remoteConfig.fetchAndActivate().await()
        val isGamePassEnabled = remoteConfig.getBoolean("game_pass")
        Log.d(REPOSITORY_TAG, "Game pass is: $isGamePassEnabled")
        this.emit(isGamePassEnabled)
    }

    companion object {
        const val REPOSITORY_TAG = "CheckGamePassRepository"
    }
}