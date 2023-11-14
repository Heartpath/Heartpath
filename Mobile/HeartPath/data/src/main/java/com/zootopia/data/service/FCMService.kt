package com.zootopia.data.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.zootopia.data.R
import java.util.Random
import kotlin.math.log


class FCMService : FirebaseMessagingService() {
    // message 받으면 호출
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(TAG, "onMessageReceived: 메시지를 받았어요")
        if(message.notification == null) {
            Log.d(TAG, "onMessageReceived: notification은 null이에요")
            Log.d(TAG, "onMessageReceived: ${message.data}")
            if(message.data.isNotEmpty()) {
                val msg = message.data.get("title")
                val mainIntent = Intent(
                    this@FCMService,
                    Class.forName("com.zootopia.presentation.MainActivity")
                ).apply {
                    putExtra("destination", "map")
                }
                val pendingIntent: PendingIntent = PendingIntent.getActivity(
                    this,
                    REQEUSTCODE,
                    mainIntent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
                )

                val notificationManager: NotificationManager =  getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    createNotificaitonChannel(notificationManager)
                }
                // 알림 생성하고 nav args에 집어넣고 아이콘 지정
                val builder =
                    NotificationCompat.Builder(this@FCMService, CHANNEL_ID)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setDefaults(NotificationCompat.DEFAULT_SOUND or NotificationCompat.DEFAULT_VIBRATE)
                        .setSmallIcon(com.google.firebase.R.drawable.notification_icon_background)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(msg)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setFullScreenIntent(pendingIntent, true)
                NotificationManagerCompat.from(this).apply {
                    Log.d(TAG, "onMessageReceived: 여기")
                    if (ActivityCompat.checkSelfPermission(
                            applicationContext,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        Log.d(TAG, "onMessageReceived: permission 문제")
                        return
                    }
                    notificationManager.notify(Random(System.nanoTime()).nextInt(), builder.build())
                }
            }
        }else{
            Log.d(TAG, "onMessageReceived: not null")
            //        // message에 notification 포함되어 있는지 확인
            message.notification?.let { notification ->
                Log.d(TAG, "onMessageReceived: ${notification.body}")
            }
        }

    }

    private fun createNotificaitonChannel(manager: NotificationManager) {
        var notificationChannel : NotificationChannel? = null
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationChannel != null) {
                notificationChannel.description = NOTIFICATION_CHANNEL_DESCRIPTION
            }
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationChannel != null) {
                manager.createNotificationChannel(notificationChannel)
            }
        }
    }

    // token update되면 호출
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "onNewToken: ${token}")
    }

    companion object {
        private const val TAG = "FCMService_HP"
        private const val REQEUSTCODE = 1
        private const val CHANNEL_ID = "heartpath_channel"
        private const val CHANNEL_NAME = "Heart Path"
        private const val NOTIFICATION_CHANNEL_DESCRIPTION = "notification channel"
    }
}