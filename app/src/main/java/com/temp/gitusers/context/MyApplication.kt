package com.temp.gitusers.context

import android.app.Application
import com.temp.gitusers.context.injection.AppModule
import com.temp.gitusers.history.room.di.DatabaseModule
import com.temp.gitusers.history.view.HistoryFragment
import com.temp.gitusers.main.view.MainFragment
import com.temp.gitusers.main.viewmodel.di.ApiModule
import com.temp.gitusers.selectedUser.view.SelectedUserFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class, DatabaseModule::class])
interface AppComponent {
    fun inject(mainFragment: MainFragment)
    fun inject(selectedUserFragment: SelectedUserFragment)
    fun inject(historyFragment: HistoryFragment)
}

class MyApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}