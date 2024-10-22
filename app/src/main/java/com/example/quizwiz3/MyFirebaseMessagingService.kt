package com.example.quizwiz3

import android.annotation.SuppressLint
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.util.Log
import android.os.Build
import android.app.NotificationChannel
import androidx.core.app.NotificationCompat
import android.app.NotificationManager
import android.content.Context
import android.content.Intent

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Check if message contains a notification payload
        remoteMessage.notification?.let {
            sendNotification(it.title, it.body)
        }
    }

    private fun sendNotification(title: String?, messageBody: String?) {
        // Define your channel ID and title for consistency
        val channelId = "default_channel_id"  // Ensure this ID is unique to your app
        val channelName = "General Notifications"  // Define the channel name

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.quizwizlogo)  // Ensure this drawable exists
            .setContentTitle(title ?: "Notification")  // Fallback to a default title
            .setContentText(
                messageBody ?: "You have a new message."
            )  // Fallback to a default message
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)  // Dismiss notification after clicking

// Get the notification manager service
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

// For Android Oreo and above, set the notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "General notifications for the app"
            }
            notificationManager.createNotificationChannel(channel)
        }

// Display the notification with a unique ID
        notificationManager.notify(0, notificationBuilder.build())
    }
}