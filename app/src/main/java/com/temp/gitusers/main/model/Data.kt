package com.temp.gitusers.main.model

import com.google.gson.annotations.SerializedName

data class RawUser(
    @SerializedName("avatar_url") val avatarUrl: String,
    val login: String,
    val id: Long
)