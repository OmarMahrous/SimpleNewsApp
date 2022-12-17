package com.itworxeducation.simplenewsapp.data.repository

import com.itworxeducation.simplenewsapp.data.source.remote.ApiUtil
import com.itworxeducation.simplenewsapp.data.source.remote.onboarding.OnboardingSourcesApi

class OnboardingSourcesRepository constructor(private val onboardingSourcesApi: OnboardingSourcesApi){
    suspend fun getSources() = onboardingSourcesApi.getSources(ApiUtil.API_KEY)
}