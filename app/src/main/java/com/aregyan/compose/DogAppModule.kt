package com.aregyan.compose

import com.aregyan.compose.network.DogsApi
import com.aregyan.compose.repository.DogsRepository
import com.aregyan.compose.ui.dogs.DogsViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

@OptIn(ExperimentalSerializationApi::class)
val dogAppModule = module {
    single {
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
        } else {
            OkHttpClient.Builder().build()
        }
    }
    single {
        Retrofit.Builder().addConverterFactory(Json {
                explicitNulls = false
                ignoreUnknownKeys = true
            }.asConverterFactory("application/json".toMediaType()))
            .baseUrl("https://raw.githubusercontent.com/").client(get()).build()
    }
    single {
        val retrofit: Retrofit = get()
        retrofit.create(DogsApi::class.java)
    }
    single {
        DogsRepository(get())
    }
    single {
        DogsViewModel(get())
    }
}