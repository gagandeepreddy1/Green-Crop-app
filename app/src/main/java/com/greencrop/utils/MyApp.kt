package com.greencrop.utils

import android.app.Application
import androidx.multidex.MultiDex
import com.greencrop.activities.login.ui.login.SharedPrefManager

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this);
        pref = SharedPrefManager()
        pref!!.SharedPrefManager(this)
    }

    companion object {
        var pref: SharedPrefManager? = null
    }
}