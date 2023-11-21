package com.sekhgmainuddin.assignmentmoengage.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.sekhgmainuddin.assignmentmoengage.presentation.main.MainActivity

// This is for FCM Notifications
class NotificationService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("MESSAGERECEIVED", "onMessageReceived: RECEIVED")
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, "NEW_CHANNEL_ID")
        val resultIntent: Intent? = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent? =
            PendingIntent.getActivity(this, 1, resultIntent, PendingIntent.FLAG_IMMUTABLE)
        builder.setAutoCancel(true)
        builder.setContentIntent(pendingIntent)
        val mNotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "NEW_CHANNEL_ID"
        builder.setContentText(message.notification?.body.toString())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "MoEngage Assignment",
                NotificationManager.IMPORTANCE_HIGH
            )
            mNotificationManager.createNotificationChannel(channel)
        }
        builder.setChannelId(channelId)
        mNotificationManager.notify(100, builder.build())
    }
}