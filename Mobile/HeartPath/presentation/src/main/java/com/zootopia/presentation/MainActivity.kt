package com.zootopia.presentation

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.zootopia.presentation.config.BaseActivity
import com.zootopia.presentation.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    
    lateinit var navController: NavController
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    
        val navHostFragmentManager = supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        navController = navHostFragmentManager.navController
    }
}
