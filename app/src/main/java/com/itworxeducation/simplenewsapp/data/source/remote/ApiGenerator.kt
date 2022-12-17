package com.itworxeducation.simplenewsapp.data.source.remote

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiGenerator {

    private val BASE_URL = "https://newsapi.org/v2/top-headlines/"

    fun <T> setupBaseApi(apiClass: Class<T>): T {

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(apiClass)
    }


}