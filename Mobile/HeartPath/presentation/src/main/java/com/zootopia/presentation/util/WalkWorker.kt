package com.zootopia.presentation.util

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.zootopia.presentation.MainActivity
import com.zootopia.presentation.R
import com.zootopia.presentation.map.MapFragment
import kotlinx.coroutines.delay

private const val TAG = "WalkWorker_HP"

class WalkWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {

    private var dist = 0F
    private var time = 0
    private val handler = Handler(Looper.getMainLooper())
    private val notificationContent = "산책 시작!"

    // marker posi
    private lateinit var goalLocation: Location
    private lateinit var userLocation: Location

    private val locationManager by lazy {
        applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        Log.d(TAG, "doWork:시작")
        val inputData = inputData // 데이터 가져오기
        goalLocation = Location("goalProvider")
        userLocation = Location("userProvider")
        goalLocation.latitude = inputData.getDouble("goalLatitude", 0.0) // 목표 위치의 경도
        goalLocation.longitude = inputData.getDouble("goalLongitude", 0.0) // 목표 위치의 위도
        userLocation.latitude = inputData.getDouble("userLatitude", 0.0)
        userLocation.longitude = inputData.getDouble("userLongitude", 0.0)
        dist = userLocation.distanceTo(goalLocation)
        Log.d(TAG, "doWork: $dist")

        val foregroundInfo = createForegroundInfo(notificationContent)
        setForegroundAsync(foregroundInfo)

        handler.post {
            getProviders()
        }
        simulateLocationUpdates()

        return Result.success()
    }

    private fun createForegroundInfo(progress: String): ForegroundInfo {
        createNotificationChannel()

        val title = applicationContext.getString(R.string.app_name)

        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra("fragment_id", R.id.action_homeFragment_to_mapFragment)
        // flag 적용해야함 :  Android 31 이상의 버전에서 보안 및 호환성을 향상하기 위함
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(applicationContext, FOREGROUND_SERVICE_ID.toString())
            .setContentTitle("지금은 산책중입니다~")
            .setTicker(title)
            .setContentText(progress)
            .setContentText(progress)
            .setSmallIcon(R.drawable.image_bird_delivery)
            .setOngoing(true)
            .setContentIntent(pendingIntent)
            .build()

        return ForegroundInfo(FOREGROUND_SERVICE_ID, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager: NotificationManager =
                applicationContext.getSystemService(NotificationManager::class.java)
            val serviceChannel = NotificationChannel(
                FOREGROUND_SERVICE_ID.toString(),
                applicationContext.getString(R.string.app_name),
                NotificationManager.IMPORTANCE_LOW,
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun simulateLocationUpdates() {
        while (true) {
            delay(2000) // Simulate delay between updates
            dist = userLocation.distanceTo(goalLocation)
            time++

            MapFragment.apply {
                walkDist = dist
                walkTime = time
            }

            Log.d(TAG, "simulateLocationUpdates: time: $time  / dist: $dist")
            updateNotification("시간: ${timeIntToString(time)} \n 거리: ${distanceIntToString(dist.toInt())}")
        }
    }

    private fun updateNotification(content: String) {
        val foregroundInfo = createForegroundInfo(content)
        setForegroundAsync(foregroundInfo)
    }

    @SuppressLint("MissingPermission")
    private fun getProviders() {
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0,
            0f,
            listener,
        )
    }

    private val listener = object : LocationListener {
        // 위치가 변경될때 호출될 method
        override fun onLocationChanged(location: Location) {
            when (location.provider) {
                LocationManager.GPS_PROVIDER -> {
                    userLocation = location
                    Log.d(TAG, "onLocationChanged: ${userLocation.latitude} / ${userLocation.longitude}")
                }
            }
        }

        @SuppressLint("MissingPermission")
        override fun onProviderEnabled(provider: String) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
        }
    }

    companion object {
        const val FOREGROUND_SERVICE_ID = 12345
    }
}
