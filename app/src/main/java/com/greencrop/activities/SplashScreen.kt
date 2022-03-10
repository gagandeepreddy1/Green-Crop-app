package com.greencrop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.greencrop.activities.HomeScreen.HomeActivity
import com.greencrop.R
import com.greencrop.utils.MyApp
import com.greencrop.activities.login.ui.login.LoginActivity
import kotlinx.coroutines.*

class SplashScreen : AppCompatActivity() {
    val activityScope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        activityScope.launch {
            delay(2000)
            if (!MyApp.pref?.getemail().equals("")) {

                var intent = Intent(this@SplashScreen, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                var intent = Intent(this@SplashScreen, LoginActivity::class.java)
                startActivity(intent)
                finish()

            }
        }
    }


}