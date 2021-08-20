package com.mqa.smartspeaker

import android.app.Application
import android.content.ContextWrapper
import com.pixplicity.easyprefs.library.Prefs
import com.tuya.smart.home.sdk.TuyaHomeSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        // Initialize the Prefs class
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()

        TuyaHomeSdk.init(this)
        TuyaHomeSdk.setDebugMode(true)
    }
}