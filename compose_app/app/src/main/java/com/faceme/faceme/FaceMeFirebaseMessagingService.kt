package com.faceme.faceme

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FaceMeFirebaseMessagingService : FirebaseMessagingService() {
    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i("FaceMeFirebaseService ", "Refreshed token :: $token")
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        // TODO: Send InstanceToken ID to SNS and register a new endpoint
        registerWithSNS()
    }

    private fun registerWithSNS() {
        TODO("I'm brainded")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.i("FaceMeFirebaseService ", "Message :: $message")
        if (message.data.isNotEmpty()) {
            Log.i("FaceMeSNSService","Message data payload: " + message.data);
        }
    }
}