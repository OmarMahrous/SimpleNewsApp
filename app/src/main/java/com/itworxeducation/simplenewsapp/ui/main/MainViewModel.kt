package com.itworxeducation.simplenewsapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itworxeducation.simplenewsapp.data.source.local.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
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