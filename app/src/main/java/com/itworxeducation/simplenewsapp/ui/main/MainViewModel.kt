package com.itworxeducation.simplenewsapp.ui.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itworxeducation.simplenewsapp.data.source.local.PreferencesManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel(){

    private val preferencesFlow = preferencesManager.preferencesFlow

    fun saveIsCalledFirstTime(isCalledFirstTime:Boolean){
        viewModelScope.launch {
            preferencesManager.setIsCalledFirstTime(isCalledFirstTime)
        }

    }

    suspend fun isCalledFirstTime(): Boolean {
            return preferencesFlow.first().isCalledFirstTime
    }

}