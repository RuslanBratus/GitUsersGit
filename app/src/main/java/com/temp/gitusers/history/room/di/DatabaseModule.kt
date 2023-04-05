package com.temp.gitusers.history.room.di

import android.app.Application
import androidx.room.Room
import com.temp.gitusers.history.room.HistoryUsersDao
import com.temp.gitusers.history.room.HistoryUsersDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(myApplication: Application): HistoryUsersDatabase {
        synchronized(HistoryUsersDatabase::class.java) {
            return Room.databaseBuilder(
                myApplication.applicationContext,
                HistoryUsersDatabase::class.java, "history_users_table"
            ).build()
        }
    }

    @Provides
    @Singleton
    fun provideHistoryUsersDao(database: HistoryUsersDatabase): HistoryUsersDao = database.favouriteImagesDao()
}
