package com.temp.gitusers.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.temp.gitusers.history.model.HistoryUser
import com.temp.gitusers.history.room.HistoryUsersDatabase
import com.temp.gitusers.main.viewmodel.repository.network.MainRepository
import com.temp.gitusers.selectedUser.model.SelectedUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val historyUsersDB: HistoryUsersDatabase): ViewModel() {

    val users =
        Pager(config = PagingConfig(pageSize = 20), pagingSourceFactory = {
            mainRepository
        }).flow.cachedIn(viewModelScope)

    private val _currentUser = MutableLiveData<SelectedUser>()
    val currentUser get() = _currentUser

    private val _historyUsers = MutableLiveData<List<HistoryUser>>()
    val historyUsers get() = _historyUsers

    fun getUserInfo(userLogin: String) {
        viewModelScope.launch {
            _currentUser.postValue(mainRepository.getUser(userLogin))
        }
    }

    fun getUsersFromDB() {
        CoroutineScope(Dispatchers.IO).launch {
            _historyUsers.postValue(historyUsersDB.favouriteImagesDao().getAll())
        }
    }

    fun saveUserToDB(historyUser: HistoryUser) {
        CoroutineScope(Dispatchers.IO).launch {
            historyUsersDB.favouriteImagesDao().insertUser(historyUser)
        }
    }
}