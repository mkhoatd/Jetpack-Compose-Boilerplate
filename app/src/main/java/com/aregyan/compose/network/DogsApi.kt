package com.aregyan.compose.network

import com.aregyan.compose.network.model.DogApiModel
import retrofit2.http.GET

interface DogsApi {
    @GET("/DevTides/DogsApi/master/dogs.json")
    suspend fun getDogs(): List<DogApiModel>
}