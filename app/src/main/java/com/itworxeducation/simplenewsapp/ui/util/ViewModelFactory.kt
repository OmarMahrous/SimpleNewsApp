package com.itworxeducation.simplenewsapp.ui.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itworxeducation.simplenewsapp.data.repository.ArticlesRepository
import com.itworxeducation.simplenewsapp.data.repository.OnboardingSourcesRepository
import com.itworxeducation.simplenewsapp.ui.articles.ArticlesViewModel
import com.itworxeducation.simplenewsapp.ui.onboarding_sources.SourcesViewModel

class ViewModelFactory : ViewModelProvider.Factory {

    private var sourcesRepository:OnboardingSourcesRepository?=null
    private var articlesRepository:ArticlesRepository?=null

    constructor(sourcesRepository: OnboardingSourcesRepository){
        this.sourcesRepository = sourcesRepository
    }

    constructor(articlesRepository: ArticlesRepository){
        this.articlesRepository = articlesRepository
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SourcesViewModel::class.java)){
            sourcesRepository?.let { SourcesViewModel(it) } as T
        }else if (modelClass.isAssignableFrom(ArticlesViewModel::class.java)){
            articlesRepository?.let { ArticlesViewModel(it) } as T
        }else
            throw IllegalArgumentException("ViewModel not found")
    }
}