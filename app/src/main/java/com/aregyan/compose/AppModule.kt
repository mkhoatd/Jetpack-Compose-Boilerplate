package com.aregyan.compose

import androidx.room.Room
import com.aregyan.compose.database.AppDatabase
import com.aregyan.compose.network.DetailsApi
import com.aregyan.compose.network.UsersApi
import com.aregyan.compose.repository.DetailsRepository
import com.aregyan.compose.repository.UsersRepository
import com.aregyan.compose.ui.details.DetailsViewModel
import com.aregyan.compose.ui.users.UsersViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
    single {
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        } else {
            OkHttpClient
                .Builder()
                .build()
        }
    }
    single {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://api.github.com/")
            .client(get())
            .build()
    }
    single {
        val retrofit: Retrofit = get()
        retrofit.create(UsersApi::class.java)
    }
    single {
        val retrofit: Retrofit = get()
        retrofit.create(DetailsApi::class.java)
    }
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "Users"
        ).fallbackToDestructiveMigration().build()
    }
    single {
        val appDatabase: AppDatabase = get()
        appDatabase.usersDao
    }
    single {
        DetailsRepository(get(), get())
    }
    single {
        UsersRepository(get(), get())
    }
    viewModel {
        UsersViewModel(get())
    }
    viewModel {
        DetailsViewModel(get(), get())
    }
}