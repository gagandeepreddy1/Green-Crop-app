package com.example.mvvmsample.api

import com.chatbot.models.LoginResponse
import com.greencrop.models.getMarkketDataModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {


    @Multipart
    @POST("login.php")
    fun login(
        @Part ("email") username: RequestBody?,
        @Part ("password") password: RequestBody?,
    ): Call<String>?

    @Multipart
    @POST("registration.php")
    fun register(
        @Part ("name") name: RequestBody?,
        @Part ("email") username: RequestBody?,
        @Part ("password") password: RequestBody?,
    ): Call<LoginResponse>?

    @Multipart
    @POST("history.php")
    fun history(
        @Part("question") question: RequestBody?,
        @Part("datetime") datetime: RequestBody?,
        @Part("userID") userID: RequestBody?,
    ): Call<LoginResponse>?

    @Multipart
    @POST("deleteUser.php")
    fun deleteUser(
        @Part("email") email: RequestBody?,
    ): Call<LoginResponse>?

    @Multipart
    @POST("getMarketData.php")
    fun getMarketData(
        @Part("city") city: RequestBody?,
        @Part("type") type: RequestBody?,
    ): Call<ArrayList<getMarkketDataModel>>?

@Multipart
    @POST("getNameWiseData.php")
    fun getMLData(
        @Part("name") name: RequestBody?,
    ): Call<ArrayList<getMarkketDataModel>>?

}