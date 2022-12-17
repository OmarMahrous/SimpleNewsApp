package com.itworxeducation.simplenewsapp.data.source.remote.articles

import com.itworxeducation.simplenewsapp.data.source.remote.ApiUtil
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticlesApi {

    @GET
    suspend fun getArticles(@Query("country")country:String, @Query("apiKey")apiKey:String) : Call<ArticlesResponse>
}