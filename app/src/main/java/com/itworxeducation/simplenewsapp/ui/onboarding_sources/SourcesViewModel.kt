package com.itworxeducation.simplenewsapp.ui.onboarding_sources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itworxeducation.simplenewsapp.data.model.OnboardingSource
import com.itworxeducation.simplenewsapp.data.repository.OnboardingSourcesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SourcesViewModel constructor(
    private val sourcesRepository: OnboardingSourcesRepository)
    : ViewModel(){


    private val _sourcesEvents = MutableSharedFlow<GetSourcesEvent>(replay = 1)
    val sourcesEvents = _sourcesEvents.asSharedFlow() // read-only public view

    fun getSources(){
        viewModelScope.launch(Dispatchers.IO) {
            showLoading()

            val response = sourcesRepository.getSources()
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    val sourceResponse = response.body()
                    val responseStatus = sourceResponse?.status

                    if (responseStatus == "ok"){
                        sourceResponse.sources?.let { getData(it) }
                    }else
                        showErrorMessage(response.message())
                }else
                    showErrorMessage(response.message())

            }
        }
    }

    private suspend fun showLoading(){
        _sourcesEvents.emit(GetSourcesEvent.ShowProgressOnLoading)
    }

    private suspend fun showErrorMessage( msg:String){
        _sourcesEvents.emit(GetSourcesEvent.ShowMessageOnError(msg))
    }

    private suspend fun getData(sourceList: List<OnboardingSource>){
        _sourcesEvents.emit(GetSourcesEvent.GetDataOnSuccess(sourceList))
    }

}

sealed class GetSourcesEvent() {
    object ShowProgressOnLoading : GetSourcesEvent()

    data class GetDataOnSuccess(val sourceList: List<OnboardingSource>): GetSourcesEvent()

    data class ShowMessageOnError(val msg:String):GetSourcesEvent()

}