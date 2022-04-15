package com.faceme.faceme.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.amplifyframework.auth.AuthException
import com.amplifyframework.core.Amplify
import com.faceme.faceme.utils.rememberWindowSizeClass

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var authSuccess = false
        Amplify.Auth.fetchAuthSession(
            // On success
            { authSuccess = it.isSignedIn },
            { authSuccess = false }
        )
        Log.i("Face Me Auth", "Auth Successful? $authSuccess")

        setContent {
            val windowSizeClass = rememberWindowSizeClass()
            if (authSuccess) {
                FaceMeApp(windowSize = windowSizeClass)
            } else {
                LoginScreen(windowSize = windowSizeClass)
            }
        }
    }
}