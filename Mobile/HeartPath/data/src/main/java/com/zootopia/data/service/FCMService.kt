package com.zootopia.data.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.zootopia.data.R
import com.zootopia.data.datasource.local.PreferenceDataSource

class FCMService : FirebaseMessagingService() {
    // message 받으면 호출
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.data.isNotEmpty()) { // message가 data를 포함하고 있는지 확인
            Log.d(TAG, "onMessageReceived: ${message.data}")
        }

//        // message에 notification 포함되어 있는지 확인
//        message.notification?.let {notification ->
//            Log.d(TAG, "onMessageReceived: ${notification.body}")
//            notification.body?.let {body -> sendNotification(body)}
//        }

        val message = "편지가 도착했습니다."
        val navBuilder = NavDeepLinkBuilder(this@FCMService)
        val mainIntent = Intent(this@FCMService, Class.forName("com.zootopia.presentation.MainActivity")).apply {
            putExtra("destination","map")
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            REQEUSTCODE,
            mainIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
        )
        // 알림 생성하고 nav args에 집어넣고 아이콘 지정
        val builder =
            NotificationCompat.Builder(this@FCMService, CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_SOUND or NotificationCompat.DEFAULT_VIBRATE)
                .setSmallIcon(com.google.firebase.R.drawable.notification_icon_background)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setFullScreenIntent(pendingIntent, true)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(101, builder.build())
    }

    // token update되면 호출
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "onNewToken: ${token}")

    }

    companion object {
        private const val TAG = "FCMService_HP"
        private const val REQEUSTCODE = 0
        private const val CHANNEL_ID = "heartpath_channel"
    }
}