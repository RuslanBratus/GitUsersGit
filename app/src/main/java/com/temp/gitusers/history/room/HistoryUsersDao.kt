package com.temp.gitusers.history.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.temp.gitusers.history.model.HistoryUser

@Dao
interface HistoryUsersDao {

    @Query("SELECT * FROM history_users_table")
    fun getAll(): List<HistoryUser>?

    @Insert
    fun insertUser(historyUser: HistoryUser)
}