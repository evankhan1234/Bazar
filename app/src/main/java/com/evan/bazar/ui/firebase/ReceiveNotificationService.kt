package com.evan.bazar.ui.firebase

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.evan.bazar.R
import com.evan.bazar.ui.spalash.SplashActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson

class ReceiveNotificationService : FirebaseMessagingService() {
    val TAG = "ReceiveNotificationService"

    @SuppressLint("LongLogTag")
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "OnReceive: ${remoteMessage.from}")
        Log.d(TAG, "OnReceive: ${remoteMessage.data.get("title")}")
        Log.d(TAG, "OnReceive: ${remoteMessage.data.get("body")}")
        Log.e("OnReceive", "OnReceive: " + Gson().toJson(remoteMessage))

//        if (remoteMessage.notification != null) {
//            showNotification(remoteMessage.notification?.title, remoteMessage.notification?.body)
//        }
        showNotificationAgain(remoteMessage.data.get("title"), remoteMessage.data.get("body"))
    }



    private fun showNotificationAgain(title: String?, body: String?) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val NOTIFICATION_CHANNEL_ID = "DOKAN"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "DOKAN NOTIFICATION",
                NotificationManager.IMPORTANCE_MAX
            )
            notificationChannel.description = "Evan Channel"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor=(Color.RED)
            notificationChannel.vibrationPattern= longArrayOf(0,1000,500,500)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

        }
        val notificationBuilder=NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
         notificationBuilder
             .setDefaults(Notification.DEFAULT_ALL)
             .setWhen(System.currentTimeMillis())
             .setSmallIcon(R.drawable.email)
             .setColor(ContextCompat.getColor(getApplicationContext(), R.color.link_color))
            .setContentTitle(title)
            .setContentText(body)

        notificationManager.notify(0, notificationBuilder.build())
    }

}