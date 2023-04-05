package com.temp.gitusers.history.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.temp.gitusers.history.model.HistoryUser
import javax.inject.Singleton

@Singleton
@Database(entities = [HistoryUser::class], version = 1)
abstract class HistoryUsersDatabase: RoomDatabase() {
    abstract fun favouriteImagesDao(): HistoryUsersDao
}