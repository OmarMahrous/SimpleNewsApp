package com.itworxeducation.simplenewsapp.data.source.remote.articles

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticlesApi {

    @GET("top-headlines")
    suspend fun getArticles(@Query("category")category: String,
                            @Query("country")country:String,
                            @Query("apiKey")apiKey:String) : Response<ArticlesResponse>
}