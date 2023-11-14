package com.zootopia.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.work.WorkManager
import com.kakao.sdk.common.util.Utility
import com.zootopia.presentation.config.BaseActivity
import com.zootopia.presentation.databinding.ActivityMainBinding
import com.zootopia.presentation.util.checkAllPermission
import com.zootopia.presentation.util.showPermissionDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "MainActivity_HeartPath"

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    lateinit var navController: NavController
    lateinit var navGraph: NavGraph
    private val mainViewModel: MainViewModel by viewModels()
    private val intent: Intent = Intent()
    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Splash)
        super.onCreate(savedInstanceState)
        initMediaPlayer()
        
        initNavHost()
        initCheckPermission()
        initCollect()
        initCheck()

//        initAppbar()

        initNotification()
        initData()
        // 카카오 키 해시 값 가지고 오기
        Log.d(TAG, "KAKAO keyhash : ${Utility.getKeyHash(this)}")
    }
    
    private fun initMediaPlayer(){
        mediaPlayer = MediaPlayer.create(this, R.raw.my_precious_teddy_bear)
        mediaPlayer.isLooping = true
    }
    
    override fun onDestroy() {
        // 앱이 종료되면 백그라운드 서비스 취소하기
        WorkManager.getInstance(applicationContext).cancelAllWork()
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        if(mediaPlayer.isPlaying){
            mediaPlayer.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
        if(mainViewModel.bgmState.value == true && mediaPlayer.isPlaying == false){
            mediaPlayer.start()
        }else if(mainViewModel.bgmState.value == false && mediaPlayer.isPlaying){
            mediaPlayer.pause()
        }
    }

    private fun initNavHost() {
        val navHostFragmentManager =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        navController = navHostFragmentManager.navController
//            navGraph = navController.graph
//            initCheck()
        val inflater = navHostFragmentManager.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)

        val type = intent.getStringExtra("destination")
        if(type != null) {
            navController.navigate(R.id.mapFragment)
        } else {
            lifecycleScope.launch {
                mainViewModel.accessToken.collect { value ->
                    Log.d(TAG, "initCheck: access token $value")
                    if (value != "") {
//                    graph.setStartDestination(R.id.homeFragment)
                        navController.currentDestination?.let { it1 -> navController.popBackStack(it1.id, true) }   // 백스택 지움
                        navController.navigate(R.id.homeFragment)
                    } else {
//                    graph.setStartDestination(R.id.loginFragment)
                        navController.navigate(R.id.loginFragment)
                    }
                }
            }
        }

    }

    // 초기 권한 요청
    private fun initCheckPermission() {
        checkAllPermission(
            fragment = null,
            activity = this,
            mainViewModel = mainViewModel,
            permissionList = INIT_PERMISSION_REQUEST,
        )
    }

    // 권한 확인 다이얼로그
    private fun initCollect() {
        mainViewModel.apply {
            lifecycleScope.launch {
                isShowPermissionDialog.collectLatest {
                    Log.d(TAG, "isShowPermissionDialog collect... $it")
                    if (it) {
                        showPermissionDialog(this@MainActivity)
                        setIsShowPermissionDialog(false)
                    }
                }
            }
            lifecycleScope.launch {
                bgmState.collectLatest {
                    Log.d(TAG, "initCollect: bgm state ${it}")
                    Log.d(TAG, "initCollect: ? ${mediaPlayer.isPlaying}")
                    if(it && mediaPlayer.isPlaying == false){
                        Log.d(TAG, "initCollect: start bgm")
                        mediaPlayer.start()
                    }else if (it == false && mediaPlayer.isPlaying){
                        mediaPlayer.pause()
                    }
                }
            }
        }
    }

    // noticiation manager 초기화
    private fun initNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.default_notification_channel_name)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(
                NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_LOW,
                ),
            )
        }

        intent.extras?.let {
            for (key in it.keySet()) {
                val value = intent.extras?.getString(key)
                Log.d(TAG, "Key: $key Value: $value")
            }
        }
    }

    private fun initData(){
        mainViewModel.getBgmState()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG, "onNewIntent: 여기 호출되었어요")
    }

    // 코틀린의 전역변수
    companion object {
        const val CAMERA_PERMISSION_REJECTED = android.Manifest.permission.CAMERA // 카메라
        const val GALLERY_PERMISSION_REJECTED =
            android.Manifest.permission.READ_EXTERNAL_STORAGE // 갤러리
        const val IMAGE_PERMISSION_REJECTED = android.Manifest.permission.READ_MEDIA_IMAGES
        val PERMISSION_LIST_UNDER32 = arrayOf(
            CAMERA_PERMISSION_REJECTED,
            GALLERY_PERMISSION_REJECTED,
        )
        val PERMISSION_LIST_UP33 = arrayOf(
            CAMERA_PERMISSION_REJECTED,
            IMAGE_PERMISSION_REJECTED,
        )

        // Location
        const val ACCESS_COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION
        const val ACCESS_FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION
        val PERMISSION_LOCATION = arrayOf(
            ACCESS_COARSE_LOCATION,
            ACCESS_FINE_LOCATION,
        )

        // Notification
        const val POST_NOTIFICATIONS = android.Manifest.permission.POST_NOTIFICATIONS

        // init permission request
        val INIT_PERMISSION_REQUEST = arrayOf(
            ACCESS_COARSE_LOCATION,
            ACCESS_FINE_LOCATION,
            POST_NOTIFICATIONS,
        )
    }

    // 화면 터치했을 때 키보드 내리기
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val inputMethodManager: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        if (currentFocus is EditText) { // 외부에 클릭했을 때 edit text focus 제거
            currentFocus!!.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun initCheck() {
        mainViewModel.getAccessToken()
    }
}
