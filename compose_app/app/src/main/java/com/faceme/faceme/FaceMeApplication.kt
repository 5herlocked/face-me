package com.faceme.faceme

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.AccountOwner
import com.faceme.faceme.data.AppContainer
import com.faceme.faceme.data.AppContainerImpl

class FaceMeApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }

//    override fun onCreate() {
//        super.onCreate()
//
//        lateinit var container: AppContainer
//
//        try {
//            Amplify.addPlugin(AWSCognitoAuthPlugin())
//            Amplify.addPlugin(AWSApiPlugin())
//            Amplify.configure(applicationContext)
//
//            Log.i("FaceMe", "Initialized Amplify")
//        } catch (error: AmplifyException) {
//            Log.e("FaceMe", "Could not initialize Amplify", error)
//        }
//    }
}