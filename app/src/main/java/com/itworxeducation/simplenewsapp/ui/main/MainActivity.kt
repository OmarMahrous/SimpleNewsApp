package com.itworxeducation.simplenewsapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.itworxeducation.simplenewsapp.R
import com.itworxeducation.simplenewsapp.data.repository.OnboardingSourcesRepository
import com.itworxeducation.simplenewsapp.data.source.remote.ApiGenerator
import com.itworxeducation.simplenewsapp.data.source.remote.onboarding.OnboardingSourcesApi
import com.itworxeducation.simplenewsapp.ui.onboarding_sources.SourcesViewModel
import com.itworxeducation.simplenewsapp.ui.ViewModelFactory
import com.itworxeducation.simplenewsapp.ui.onboarding_sources.confirmation_dialog.ConfirmSelectionDialogFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"



    private val viewModel:MainViewModel by viewModels()


    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setupScreensNavigation()


        val sourcesApi = ApiGenerator.setupBaseApi(OnboardingSourcesApi::class.java)
        val sourcesRepository = OnboardingSourcesRepository(sourcesApi)

        fetchSources(sourcesRepository)

        isCalledFirstTime()

    }

    /**
     * Check if the user opens the app on the first time.
     */
    private fun isCalledFirstTime(){
        lifecycleScope.launchWhenStarted {
            if(!viewModel.isCalledFirstTime())
                navigateToArticlesPage()

        }
    }

    private fun navigateToArticlesPage() {
        val action = ConfirmSelectionDialogFragmentDirections.actionConfirmationDialogfragmentToArticlesFragment()
        navController.navigate(action)
    }



    private fun setupScreensNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.findNavController()

        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun fetchSources(sourcesRepository: OnboardingSourcesRepository) {
        val sourcesViewModel = ViewModelProvider(this, ViewModelFactory(sourcesRepository))[SourcesViewModel::class.java]
        sourcesViewModel.getSources()
    }





}