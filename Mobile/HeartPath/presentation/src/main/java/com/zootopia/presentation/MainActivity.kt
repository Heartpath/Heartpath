package com.zootopia.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zootopia.presentation.config.BaseActivity
import com.zootopia.presentation.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
