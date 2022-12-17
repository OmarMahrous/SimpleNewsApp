package com.itworxeducation.simplenewsapp.ui.articles

import androidx.lifecycle.ViewModel
import com.itworxeducation.simplenewsapp.data.repository.ArticlesRepository

class ArticlesViewModel(
    private val articlesRepository: ArticlesRepository
) : ViewModel() {
}