package com.temp.gitusers.selectedUser.model

import com.google.gson.annotations.SerializedName

data class SelectedUser (
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("organizations_url") val organizationUrl: String,
    val name: String,
    val type: String,
    val email: String?,
    val following: Long,
    val followers: Long
)

data class Organization(
    @SerializedName("avatar_url") val avatarUrl: String,
    val login: String,
    val id: Long
)