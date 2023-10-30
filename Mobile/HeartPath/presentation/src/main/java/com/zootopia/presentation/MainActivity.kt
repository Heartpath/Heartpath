package com.zootopia.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.zootopia.presentation.config.BaseActivity
import com.zootopia.presentation.databinding.ActivityMainBinding
import com.zootopia.presentation.util.checkAllPermission
import com.zootopia.presentation.util.showPermissionDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "MainActivity_HeartPath"
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    
    lateinit var navController: NavController
    private val mainViewModel: MainViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavHost()
        initCheckPermission()
        initCollect()
    }
    
    private fun initNavHost() {
        val navHostFragmentManager = supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        navController = navHostFragmentManager.navController
    }
    
    private fun initCheckPermission() {
        checkAllPermission(
            fragment = null,
            activity = this,
            mainViewModel = mainViewModel,
            permissionList = PERMISSION_LOCATION,
        )
    }
    
    // 권한 확인 다이얼로그
    private fun initCollect(){
        mainViewModel.apply {
            lifecycleScope.launch {
                isShowPermissionDialog.collectLatest {
                    Log.d(TAG, "isShowPermissionDialog collect... $it")
                    if(it){
                        showPermissionDialog(this@MainActivity)
                        setIsShowPermissionDialog(false)
                    }
                }
            }
        }
    }
    
    // 코틀린의 전역변수
    companion object {
        const val CAMERA_PERMISSION_REJECTED = android.Manifest.permission.CAMERA // 카메라
        const val GALLERY_PERMISSION_REJECTED = android.Manifest.permission.READ_EXTERNAL_STORAGE // 갤러리
        const val IMAGE_PERMISSION_REJECTED = android.Manifest.permission.READ_MEDIA_IMAGES
        val PERMISSION_LIST_UNDER32 = arrayOf(
            CAMERA_PERMISSION_REJECTED,
            GALLERY_PERMISSION_REJECTED
        )
        val PERMISSION_LIST_UP33 = arrayOf(
            CAMERA_PERMISSION_REJECTED,
            IMAGE_PERMISSION_REJECTED
        )
        
        // Location
        const val ACCESS_COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION
        const val ACCESS_FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION
        val PERMISSION_LOCATION = arrayOf(
            ACCESS_COARSE_LOCATION,
            ACCESS_FINE_LOCATION
        )
    }
}