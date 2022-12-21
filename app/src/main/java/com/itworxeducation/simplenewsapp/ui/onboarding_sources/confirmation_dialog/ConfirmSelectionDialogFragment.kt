package com.itworxeducation.simplenewsapp.ui.onboarding_sources.confirmation_dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.itworxeducation.simplenewsapp.R
import com.itworxeducation.simplenewsapp.data.model.sources.Category
import com.itworxeducation.simplenewsapp.ui.util.ArgumentUtil.Companion.getStringArgument
import dagger.hilt.android.AndroidEntryPoint
import java.lang.reflect.Type

/**
 * @param selectionName (detect which user has selected country or category)
 */
@AndroidEntryPoint
class ConfirmSelectionDialogFragment  : DialogFragment() {

    private val viewModel:ConfirmationDialogViewModel by viewModels()

    private val DIALOG_MESSAGE = "dialogMessage"
    private val SELECTION_NAME = "selectionName"
    private val COUNTRY_NAME = "countryName"
    private val SELECTED_CATEGORY_LIST = "selectedCategoryList"

    private var dialogMessage:String? = null

    private var selectionName:String? = null

    private var countryName:String? =null
    private var selectedCategoryList:List<Category>? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments

        dialogMessage = getStringArgument(args, DIALOG_MESSAGE)
        selectionName = getStringArgument(args, SELECTION_NAME)
        countryName = getStringArgument(args,  COUNTRY_NAME)
        selectedCategoryList = getListArgument(args)
    }

    private fun getListArgument(args: Bundle?): List<Category>? {

        val listType: Type =
            object : TypeToken<List<Category>?>() {}.type

        return Gson().fromJson(args?.getString(SELECTED_CATEGORY_LIST)
            ,listType)
    }




    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_title))
            .setMessage(dialogMessage)
            .setNegativeButton(getString(R.string.cancel), null)
            .setPositiveButton(
                getString(R.string.yes)
            ) { dialog, which ->
                when(selectionName){
                    "country"->{
                        confirmSelectCountry()
                        navigateToCategoriesPage()}
                    else ->{
                        confirmSelectCategories()
                        viewModel.setIsCalledFirstTime(false)
                        navigateToArticlesPage()
                    }
                }
            }.create()

    private fun confirmSelectCountry(){
        countryName?.let { viewModel.confirmSelectCountry(it) }
    }

    private fun confirmSelectCategories(){
        selectedCategoryList?.let {
            for (category in it){
                viewModel.confirmSelectCategories(category)
            }
        }
    }

    private fun navigateToCategoriesPage() {
        val action = ConfirmSelectionDialogFragmentDirections.actionConfirmationDialogfragmentToCategoriesFragment()
        findNavController().navigate(action)
    }

    private fun navigateToArticlesPage() {
        val action = ConfirmSelectionDialogFragmentDirections.actionConfirmationDialogfragmentToArticlesFragment()
        findNavController().navigate(action)
    }

}

