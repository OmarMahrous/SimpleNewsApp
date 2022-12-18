package com.itworxeducation.simplenewsapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.itworxeducation.simplenewsapp.R
import com.itworxeducation.simplenewsapp.data.repository.OnboardingSourcesRepository
import com.itworxeducation.simplenewsapp.data.source.remote.ApiGenerator
import com.itworxeducation.simplenewsapp.data.source.remote.onboarding.OnboardingSourcesApi
import com.itworxeducation.simplenewsapp.ui.onboarding_sources.GetSourcesEvent
import com.itworxeducation.simplenewsapp.ui.onboarding_sources.SourcesViewModel
import com.itworxeducation.simplenewsapp.ui.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"



    private val viewModel:MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        saveIsCalledFirstTime()

//        val sourcesApi = ApiGenerator.setupBaseApi(OnboardingSourcesApi::class.java)
//        val sourcesRepository = OnboardingSourcesRepository(sourcesApi)
//        val sourcesViewModel = ViewModelProvider(this, ViewModelFactory(sourcesRepository))[SourcesViewModel::class.java]
//        sourcesViewModel.getSources()
//        lifecycleScope.launchWhenStarted {
//            sourcesViewModel.getSourcesFlow.collect{event->
//                when(event){
//                    is GetSourcesEvent.GetDataOnSuccess ->{
//                        Log.i(TAG, "onCreate: sources size = ${event.sourceList.size}")
//                    }
//                    is GetSourcesEvent.ShowMessageOnError ->
//                        Log.e(TAG, "onCreate: fail to load sources ${event.msg}")
//
//                else -> Log.d(TAG, "onCreate: show loading")
//                }
//            }
//        }

        lifecycleScope.launchWhenStarted {
            Log.d(TAG, "onCreate: isCalledFirstTime = ${viewModel.isCalledFirstTime()}")
        }

    }

    /**
     * Check if the user opens the app on the first time.
     */
    private fun saveIsCalledFirstTime(){
        lifecycleScope.launchWhenStarted {
            if(viewModel.isCalledFirstTime())
                viewModel.saveIsCalledFirstTime(false)

        }
    }


}