package com.itworxeducation.simplenewsapp.ui.articles.favourite

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itworxeducation.simplenewsapp.data.model.Article
import com.itworxeducation.simplenewsapp.data.source.local.PreferencesManager
import com.itworxeducation.simplenewsapp.data.source.local.database.favourite.articles.ArticleDao
import com.itworxeducation.simplenewsapp.data.source.local.database.favourite.onboarding.SourceDao
import com.itworxeducation.simplenewsapp.ui.articles.GetArticlesEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavouriteArticlesViewModel @Inject constructor(
    private val articleDao: ArticleDao
)  : ViewModel() {
    private val state = SavedStateHandle()

val searchQuery = state.getLiveData("searchQuery", "")


suspend fun saveArticle(article: Article) = articleDao.addArticleToFavourites(article)

suspend fun removeArticle(article: Article) = articleDao.removeArticleFromFavourites(article)




    suspend fun getFavouriteArticles() = articleDao.getFavouritesArticles()

    suspend fun searchArticles(searchQuery:String) = articleDao.getArticlesSortedByDateCreated(searchQuery)

}