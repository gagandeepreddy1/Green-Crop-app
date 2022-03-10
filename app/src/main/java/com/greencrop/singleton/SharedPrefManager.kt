package com.greencrop.activities.login.ui.login

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager {
    var context: Context? = null
    var email = "email"
    var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

    fun SharedPrefManager(context: Context) {
        this.context = context
        sharedPreferences = context.getSharedPreferences("Data", Context.MODE_PRIVATE)
        editor = sharedPreferences?.edit()
    }


    fun setemail(s: String?) {
        editor!!.putString(email, s)
        editor!!.commit()
    }

    fun getemail(): String? {
        return sharedPreferences!!.getString(email, "")
    }
}