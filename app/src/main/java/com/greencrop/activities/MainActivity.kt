package com.greencrop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.greencrop.R
import com.greencrop.activities.login.SignUpActivity
import com.greencrop.activities.login.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun SignIn(v: View) {
        if (v.tag.equals("1")) {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun SignUp(v: View) {
        if (v.tag.equals("2")) {
            var intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
    }
}