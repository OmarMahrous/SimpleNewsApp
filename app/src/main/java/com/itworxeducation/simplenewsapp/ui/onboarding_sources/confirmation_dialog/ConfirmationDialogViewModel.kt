package com.itworxeducation.simplenewsapp.ui.onboarding_sources.confirmation_dialog

import androidx.lifecycle.ViewModel
import com.itworxeducation.simplenewsapp.data.model.sources.Category
import com.itworxeducation.simplenewsapp.data.source.local.database.favourite.onboarding.SourceDao
import com.itworxeducation.simplenewsapp.di.ApplicationScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmationDialogViewModel @Inject constructor(
    private val sourceDao: SourceDao,
    /** Note: We need application scope instead of view model scope
     * because if user tapped on OK in the dialog fragment it will dismissed and its viewmodel will be lost from memory
     * and the deletion process will be cancelled also.
     **/
//     private val applicationScope: CoroutineScope

)    : ViewModel() {



    fun confirmSelectCountry(countryName:String) = GlobalScope.launch {
        sourceDao.addCountryToFavourites(countryName)
    }

    fun confirmSelectCategories(category: Category) = GlobalScope.launch {
        sourceDao.addCategoryToFavourites(category)
    }

}