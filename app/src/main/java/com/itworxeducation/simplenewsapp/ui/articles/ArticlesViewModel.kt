package com.itworxeducation.simplenewsapp.ui.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itworxeducation.simplenewsapp.data.model.Article
import com.itworxeducation.simplenewsapp.data.repository.ArticlesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticlesViewModel(
    private val articlesRepository: ArticlesRepository
) : ViewModel() {

    private val _articlesChannelEvent = Channel<GetArticlesEvent>()
    val articlesFlowEvent = _articlesChannelEvent.receiveAsFlow() // read-only public view

    fun getArticles(category: String="", country:String){
        viewModelScope.launch(Dispatchers.IO) {
            showLoading()

            val response = articlesRepository.getArticles(category, country)
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

}

sealed class GetArticlesEvent() {
    object ShowProgressOnLoading : GetArticlesEvent()

    data class GetDataOnSuccess(val articleList: List<Article>): GetArticlesEvent()

    data class ShowMessageOnError(val msg:String):GetArticlesEvent()

}