package com.temp.gitusers.main.viewmodel.repository.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.temp.gitusers.main.model.RawUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(private val usersApiService: UsersApiService):
    PagingSource<Long, RawUser>() {

    suspend fun getUser(userLogin: String) = usersApiService.getUser(userLogin)

    override fun getRefreshKey(state: PagingState<Long, RawUser>): Long? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, RawUser> {
        return try {
            val sinceId = params.key ?: 0
            val response = usersApiService.getUsers(
                sinceId = sinceId,
            )

            LoadResult.Page(
                data = response,
                prevKey = if (sinceId > 0) sinceId - 1 else null,
                nextKey = if (sinceId < response[response.size-1].id) response[response.size-1].id else null
            ) //@TODO check last ID, so we cannot overGet, when no new users
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
