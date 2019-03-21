package com.pujara.dhaval.spendsmart.dashboard

import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.pujara.dhaval.spendsmart.R

class MyMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        showNotification(remoteMessage?.notification?.title,remoteMessage?.notification?.body)
    }

    private fun showNotification(title: String?, message: String?) {
        val builder = NotificationCompat.Builder(this, "MyNotifications")
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_action_add)
            .setAutoCancel(true)
            .setContentText(message)
        val manager : NotificationManagerCompat = NotificationManagerCompat.from(this)
        manager.notify(999,builder.build())
    }

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Log.d("TAG", "Refreshed token: $token");

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(token);
    }
}