package com.itworxeducation.simplenewsapp.data.model.sources

import androidx.room.Embedded
import androidx.room.Ignore
import com.google.gson.annotations.SerializedName

data class OnboardingSource(
                            @Ignore
                            @SerializedName("id")
                            val sid:String?=null,
                            @SerializedName("name")
                            val sname:String?=null,
                            @SerializedName("description")
                            val sdescription:String?=null,
                            @SerializedName("url")
                            val surl:String?=null,
                            val category:String?=null,
                            val language:String?=null,
                            val country:String?=null)
