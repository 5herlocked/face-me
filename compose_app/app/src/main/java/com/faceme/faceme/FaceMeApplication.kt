package com.faceme.faceme

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.AccountOwner

class FaceMeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.configure(applicationContext)

            Log.i("FaceMe", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("FaceMe", "Could not initialize Amplify", error)
        }
    }
}