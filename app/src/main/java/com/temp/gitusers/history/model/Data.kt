package com.temp.gitusers.history.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_users_table")
data class HistoryUser(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String,
    val email: String?
)