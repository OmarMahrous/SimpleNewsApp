package com.itworxeducation.simplenewsapp.data.model.sources

import androidx.room.Ignore
import com.google.gson.annotations.SerializedName

data class OnboardingSource(
                            @Ignore
                            @SerializedName("id")
                            var sid:String?=null,
                            @SerializedName("name")
                            var sname:String?=null,
                            @SerializedName("description")
                            var sdescription:String?=null,
                            @SerializedName("url")
                            var surl:String?=null,
                            var category:String?=null,
                            var language:String?=null,
                            var country:String?=null)
//{
//    constructor() : this("", "", "", "","", "", "")
//}


