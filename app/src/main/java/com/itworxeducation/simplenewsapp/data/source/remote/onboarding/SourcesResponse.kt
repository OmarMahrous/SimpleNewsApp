package com.itworxeducation.simplenewsapp.data.source.remote.onboarding

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.itworxeducation.simplenewsapp.data.model.sources.OnboardingSource
import com.itworxeducation.simplenewsapp.data.source.remote.BaseResponse

class SourcesResponse : BaseResponse(){

    @SerializedName("sources")
    @Expose
    val sources: List<OnboardingSource>? = null
}