package com.example.quizwiz3

import android.annotation.SuppressLint
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.util.Log
import android.os.Build
import android.app.NotificationChannel
import androidx.core.app.NotificationCompat
import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseService  : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = getString(R.string.msg_token_fmt, token)
            Log.d(TAG, msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })
    }
        override fun onNewToken(token: String) {
            Log.d(TAG, "Refreshed token: $token")


            // FCM registration token to your app server.
            sendRegistrationToServer(token)
        }
    // Sends the registration token to your app's server
    private fun sendRegistrationToServer(token: String) {
        // TODO: Implement this method to send token to your app server.
        // Example: Network call or saving to a database.
        Log.d(TAG, "Token sent to server: $token")
    }

    companion object {
        private const val TAG = "MyFirebaseService"
    }
}
