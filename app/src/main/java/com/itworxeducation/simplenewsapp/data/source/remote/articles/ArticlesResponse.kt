package com.itworxeducation.simplenewsapp.data.source.remote.articles

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.itworxeducation.simplenewsapp.data.model.Article
import com.itworxeducation.simplenewsapp.data.source.remote.BaseResponse

class ArticlesResponse : BaseResponse() {
    @SerializedName("totalResults")
    @Expose
    val totalResults:Int?=null

    @SerializedName("articles")
    @Expose
    val articles: List<Article>? = null


}