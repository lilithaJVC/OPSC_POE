package com.example.quizwiz3

import android.annotation.SuppressLint
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.util.Log
import android.os.Build
import android.app.Notification
import android.app.NotificationChannel
import androidx.core.app.NotificationCompat
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import java.io.IOException

class MyFirebaseMessagingService : FirebaseMessagingService() {


    private val TAG = "FireBaseMessagingService"
    var NOTIFICATION_CHANNEL_ID = "com.example.quizwiz3"
    val NOTIFICATION_ID = 100
    //al token = FirebaseMessaging.getInstance().getToken()

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)


        //Log.e("token","$token");

        // Retrieve the FCM token
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            Log.d(TAG, "FCM Token: $token")
        })

        Log.e("message","Message Received ...");

        if (remoteMessage.data.size > 0) {
            val title = remoteMessage.data["title"]
            Log.e("TITLE","$title");
            val body = remoteMessage.data["body"]
            Log.e("body","$body");
            showNotification(applicationContext, title, body)
        } else {
            val title = remoteMessage.notification!!.title
            val body = remoteMessage.notification!!.body

            Log.e("TITLE else","$title");
            Log.e("body else","$body");
            showNotification(applicationContext, title, body)
        }
    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("token", "New Token: $token")
    }


    private fun fetchTokenWithRetry(retryCount: Int = 0) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val newToken = task.result
                Log.d(TAG, "Retrieved token: $newToken")
            } else {
                val exception = task.exception
                if (exception is IOException && retryCount < 3) {
                    Log.d(TAG, "Retrying token retrieval...")
                    // Retry after a delay
                    Handler(Looper.getMainLooper()).postDelayed({
                        fetchTokenWithRetry(retryCount + 1)
                    }, 2000)
                } else {
                    Log.w(TAG, "Failed to retrieve token after retries: ${exception?.message}")
                }
            }
        }
    }


    fun showNotification(
        context: Context,
        title: String?,
        message: String?
    ) {
        //val ii: Intent
        val ii = Intent(context, MainActivity::class.java)
        ii.data = Uri.parse("custom://" + System.currentTimeMillis())
        ii.action = "actionstring" + System.currentTimeMillis()
        ii.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pi =
            PendingIntent.getActivity(context, 0, ii, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification: Notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setOngoing(true)
                .setSmallIcon(getNotificationIcon())
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pi)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setWhen(System.currentTimeMillis())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentTitle(title).build()
            val notificationManager = context.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                title,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
            notificationManager.notify(NOTIFICATION_ID, notification)
        } else {
            notification = NotificationCompat.Builder(context)
                .setSmallIcon(getNotificationIcon())
                .setAutoCancel(true)
                .setContentText(message)
                .setContentIntent(pi)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentTitle(title).build()
            val notificationManager = context.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }

    private fun getNotificationIcon(): Int {
        val useWhiteIcon =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE
        return if (useWhiteIcon) R.mipmap.ic_launcher else R.mipmap.ic_launcher
    }

}