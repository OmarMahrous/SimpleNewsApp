package com.itworxeducation.simplenewsapp.data.repository

import com.itworxeducation.simplenewsapp.data.source.remote.ApiUtil
import com.itworxeducation.simplenewsapp.data.source.remote.articles.ArticlesApi

class ArticlesRepository constructor(private val articlesApi: ArticlesApi){

    suspend fun getArticles(category: String="",country:String) =
        articlesApi.getArticles(category, country, ApiUtil.API_KEY)

}