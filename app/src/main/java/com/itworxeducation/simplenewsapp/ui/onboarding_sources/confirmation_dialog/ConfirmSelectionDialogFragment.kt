package com.itworxeducation.simplenewsapp.ui.onboarding_sources.confirmation_dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.itworxeducation.simplenewsapp.R

/**
 * @param selectionName (detect which user has selected country or category)
 */
class ConfirmSelectionDialogFragment  : DialogFragment() {

    private val DIALOG_MESSAGE = "dialogMessage"
    private val SELECTION_NAME = "selectionName"

    var dialogMessage:String? = null

    var selectionName:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialogMessage = getArgument(DIALOG_MESSAGE)
        selectionName = getArgument(SELECTION_NAME)

    }

    private fun getArgument(argName:String): String? {

        val args = arguments

        return when(argName){
            DIALOG_MESSAGE -> args?.getString(DIALOG_MESSAGE)
            else -> args?.getString(SELECTION_NAME)

        }

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
                    "country"->{navigateToCategoriesPage()}
                    else ->{ navigateToArticlesPage() }
                }
            }.create()



    private fun navigateToCategoriesPage() {
        val action = ConfirmSelectionDialogFragmentDirections.actionConfirmationDialogfragmentToCategoriesFragment()
        findNavController().navigate(action)
    }

    private fun navigateToArticlesPage() {
        val action = ConfirmSelectionDialogFragmentDirections.actionConfirmationDialogfragmentToArticlesFragment()
        findNavController().navigate(action)
    }

}

