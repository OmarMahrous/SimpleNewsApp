package com.itworxeducation.simplenewsapp.data.model.sources

import androidx.room.Embedded
import androidx.room.Ignore
import com.google.gson.annotations.SerializedName

data class OnboardingSource(
                            @SerializedName("id")
                            val sid:String,
                            @SerializedName("name")
                            val sname:String,
                            @SerializedName("description")
                            val sdescription:String,
                            @SerializedName("url")
                            val surl:String,
                            val category:String,
                            val language:String,
                            val country:String)
