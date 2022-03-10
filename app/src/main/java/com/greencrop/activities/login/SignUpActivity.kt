package com.greencrop.activities.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import com.example.mvvmsample.repositories.UserRepository
import com.greencrop.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        signUp.setOnClickListener() {
            registerUser()
        }
    }
    fun String.isEmailValid(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
    private fun registerUser() {
        val name = editname.text.toString()
        val email = editemail.text.toString().trim()
        val password = editpassword.text.toString()
        if (TextUtils.isEmpty(name)) {
            editname.setError("Please enter name")
            editname.requestFocus()
            return
        } else if (email.equals("")) {
            editemail.setError("Please enter Correct username")
            editemail.requestFocus()
            return
        } else if (!email.isEmailValid()) {
            editemail.setError("Please enter Correct username")
            editemail.requestFocus()
            return
        } else if (TextUtils.isEmpty(password)) {
            editpassword.setError("Enter a password")
            editpassword.requestFocus()
            return
        }else if (password.length<8) {
            editpassword.setError(resources.getString(R.string.invalid_password))
            editpassword.requestFocus()
            return
        }else{
            UserRepository.register(this,name,email,password)
        }

    }


}