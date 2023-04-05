package com.temp.gitusers.main.viewmodel.repository.network

import com.temp.gitusers.main.model.RawUser
import com.temp.gitusers.selectedUser.model.SelectedUser
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UsersApiService {

    @GET("/users")
    suspend fun getUsers(
        @Query("since") sinceId: Long,
        @Query("per_page") perPage: Int = 20
    ): List<RawUser>

    @GET("/users/{login}")
    suspend fun getUser(
        @Path("login") userLogin: String
    ): SelectedUser

}