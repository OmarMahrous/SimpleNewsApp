package com.itworxeducation.simplenewsapp.ui.articles

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itworxeducation.simplenewsapp.data.model.Article
import com.itworxeducation.simplenewsapp.data.repository.ArticlesRepository
import com.itworxeducation.simplenewsapp.data.source.local.database.favourite.articles.ArticleDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val articleDao: ArticleDao
) : ViewModel() {

    private lateinit var articlesRepository: ArticlesRepository

    fun setRepository(articlesRepository: ArticlesRepository) {
        this.articlesRepository = articlesRepository
    }

    private val state = SavedStateHandle()

    val searchQuery = state.getLiveData("searchQuery", "")

    private val _articlesChannelEvent = Channel<GetArticlesEvent>()
    val articlesFlowEvent = _articlesChannelEvent.receiveAsFlow() // read-only public view

    fun getArticles(category: String="", country:String, searchQuery:String?){
        viewModelScope.launch(Dispatchers.IO) {
            showLoading()

            val response = articlesRepository.getArticles(category, country,searchQuery)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    val articlesResponse = response.body()
                    val responseStatus = articlesResponse?.status

                    if (responseStatus == "ok"){
                        articlesResponse.articles?.let { getData(it) }
                    }else
                        showErrorMessage(response.message())
                }else
                    showErrorMessage(response.message())

            }
        }
    }

    private suspend fun showLoading(){
        _articlesChannelEvent.send(GetArticlesEvent.ShowProgressOnLoading)
    }

    private suspend fun showErrorMessage( msg:String){
        _articlesChannelEvent.send(GetArticlesEvent.ShowMessageOnError(msg))
    }

    private suspend fun getData(articleList: List<Article>){
        _articlesChannelEvent.send(GetArticlesEvent.GetDataOnSuccess(articleList))
    }

    suspend fun saveArticle(article: Article) = articleDao.addArticleToFavourites(article)

    suspend fun removeArticle(article: Article) = articleDao.removeArticleFromFavourites(article)

}

sealed class GetArticlesEvent() {
    object ShowProgressOnLoading : GetArticlesEvent()

    data class GetDataOnSuccess(val articleList: List<Article>): GetArticlesEvent()

    data class ShowMessageOnError(val msg:String):GetArticlesEvent()

}