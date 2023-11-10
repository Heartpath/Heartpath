package com.zootopia.data.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {
    // message 받으면 호출
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if(message.data.isNotEmpty()) { // message가 data를 포함하고 있는지 확인
            Log.d(TAG, "onMessageReceived: ${message.data}")

        }

//        // message에 notification 포함되어 있는지 확인
//        message.notification?.let {notification ->
//            Log.d(TAG, "onMessageReceived: ${notification.body}")
//            notification.body?.let {body -> sendNotification(body)}
//        }
    }

    private fun isLongRunningJob() = true

    // token update되면 호출
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "onNewToken: ${token}")
    }

    companion object {
        private const val TAG = "FCMService_HP"
    }
}