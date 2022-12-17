package com.itworxeducation.simplenewsapp.data.source.remote.onboarding

import com.itworxeducation.simplenewsapp.data.source.remote.articles.ArticlesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OnboardingSourcesApi {

    @GET("sources")
    suspend fun getSources(@Query("apiKey")apiKey:String) : Response<SourcesResponse>

}