package com.aregyan.compose.di
//
//import android.content.Context
//import androidx.room.Room
//import com.aregyan.compose.database.UsersDao
//import com.aregyan.compose.database.AppDatabase
//
//object DatabaseModule {
//    @Provides
//    @Singleton
//    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
//        return Room.databaseBuilder(
//            appContext,
//            AppDatabase::class.java,
//            "Users"
//        ).fallbackToDestructiveMigration().build()
//    }
//
//    @Provides
//    fun provideChannelDao(appDatabase: AppDatabase): UsersDao {
//        return appDatabase.usersDao
//    }
//
//}