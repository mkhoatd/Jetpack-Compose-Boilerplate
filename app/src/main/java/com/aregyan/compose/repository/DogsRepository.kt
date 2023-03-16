package com.aregyan.compose.repository

import com.aregyan.compose.domain.Dog
import com.aregyan.compose.network.DogsApi
import com.aregyan.compose.network.model.asDomainModel
import timber.log.Timber

class DogsRepository(
    private val dogsApi: DogsApi,
) {
    suspend fun refreshDogs(): List<Dog> {
        try {
            return dogsApi.getDogs().asDomainModel()
        } catch (e: Exception) {
            Timber.w(e)
            throw e
        }
    }
}