package com.temp.gitusers.context

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.temp.gitusers.R

class MainActivity : AppCompatActivity() {
    lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        navView = findViewById(R.id.bottomNavigationView)
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHost.findNavController()
        navView.setupWithNavController(navController)
    }
}