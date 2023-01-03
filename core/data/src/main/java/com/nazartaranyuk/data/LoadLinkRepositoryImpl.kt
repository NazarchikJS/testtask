package com.nazartaranyuk.data

import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.nazartaranyuk.domain.repositories.FirebaseLinkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class LoadLinkRepositoryImpl(
    private val remoteConfig: FirebaseRemoteConfig
) : FirebaseLinkRepository {

    override suspend fun loadLink() = flow<String> {
        remoteConfig.fetchAndActivate().await()
        val link = remoteConfig.getString("web_link")
        this.emit(link)
    }
}