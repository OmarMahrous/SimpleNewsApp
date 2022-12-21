package com.itworxeducation.simplenewsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itworxeducation.simplenewsapp.data.repository.ArticlesRepository
import com.itworxeducation.simplenewsapp.data.repository.OnboardingSourcesRepository
import com.itworxeducation.simplenewsapp.ui.articles.ArticlesViewModel
import com.itworxeducation.simplenewsapp.ui.onboarding_sources.SourcesViewModel

class ViewModelFactory(sourcesRepository: OnboardingSourcesRepository) : ViewModelProvider.Factory {

    private var sourcesRepository:OnboardingSourcesRepository?= sourcesRepository


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SourcesViewModel::class.java)){
            sourcesRepository?.let { SourcesViewModel(it) } as T
        }else
            throw IllegalArgumentException("ViewModel not found")
    }
}