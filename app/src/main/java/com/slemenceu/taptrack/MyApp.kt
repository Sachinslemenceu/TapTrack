package com.slemenceu.taptrack

import android.app.Application
import com.google.firebase.FirebaseApp
import com.slemenceu.taptrack.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApp: Application()  {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@MyApp)
            modules(appModule)
        }

    }
}