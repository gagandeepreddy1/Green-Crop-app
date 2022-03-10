package com.example.mvvmsample.repositories

import android.content.Context
import android.content.Intent
import android.util.Log
import com.chatbot.models.LoginResponse
import com.example.mvvmsample.api.ApiClient
import com.google.gson.GsonBuilder
import com.greencrop.activities.HomeScreen.HomeActivity
import com.greencrop.singleton.toast
import com.greencrop.utils.MyApp
import okhttp3.*;
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRepository {

    fun register(context: Context, name: String, email: String, password: String): String {
        var mutableLiveData: String = "aa"
        //context.showProgressBar()
        ApiClient.apiService.register(getPart(name), getPart(email), getPart(password))
            ?.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>,
                ) {
                    //   hideProgressBar()
                    if (response.isSuccessful) {
                        context.toast("Success")
                        Log.e("response is ",
                            GsonBuilder().setPrettyPrinting().create().toJson(response))
                        mutableLiveData = "success"

                        MyApp.pref?.setemail(email)
                        var intent = Intent(context, HomeActivity::class.java)
                        context.startActivity(intent)
                    } else {
                        context.toast("something went wrong")
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    //  hideProgressBar()
                    context.toast("onfailure" + t.localizedMessage)
                    Log.e("error onfailure ", t.localizedMessage)
                }
            })

        return mutableLiveData
    }

    fun login(context: Context, email: String, password: String): String {
        var mutableLiveData: String = "aa"
        //  context.showProgressBar()
        ApiClient.apiService.login(getPart(email), getPart(password))
            ?.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    //  hideProgressBar()
                    if (response.isSuccessful) {
                        context.toast("Success")
                        Log.e("response is ",
                            GsonBuilder().setPrettyPrinting().create().toJson(response))
                        mutableLiveData = "success"
                        MyApp.pref?.setemail(email)
                        var intent = Intent(context, HomeActivity::class.java)
                        context.startActivity(intent)
                    } else {
                        context.toast("something went wrong")
                    }

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    //  hideProgressBar()
                    mutableLiveData = t.localizedMessage
                    context.toast("onfailure" + t.localizedMessage)
                    Log.e("error onfailure ", t.localizedMessage)
                }
            })

        return mutableLiveData
    }



    private fun getPart(name: String?): RequestBody? {
        return if (name == null) {
            RequestBody.create(MediaType.parse("text/plain"), "")
        } else {
            RequestBody.create(MediaType.parse("text/plain"), name)
        }
    }

}