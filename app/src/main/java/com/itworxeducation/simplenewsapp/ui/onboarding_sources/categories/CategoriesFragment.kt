package com.itworxeducation.simplenewsapp.ui.onboarding_sources.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.itworxeducation.simplenewsapp.R
import com.itworxeducation.simplenewsapp.data.model.Category
import com.itworxeducation.simplenewsapp.databinding.FragmentCategoriesBinding
import com.itworxeducation.simplenewsapp.ui.BaseFragment
import com.itworxeducation.simplenewsapp.ui.onboarding_sources.GetSourcesEvent
import com.itworxeducation.simplenewsapp.ui.onboarding_sources.SourcesViewModel
import com.itworxeducation.simplenewsapp.ui.onboarding_sources.countries.CountriesFragmentDirections
import com.itworxeducation.simplenewsapp.ui.util.ListMapper

class CategoriesFragment: BaseFragment(R.layout.fragment_categories) {

    private val TAG = "CategoriesFragment"

    private val viewModel: SourcesViewModel by activityViewModels()


    private var _binding: FragmentCategoriesBinding? =null
    private val binding: FragmentCategoriesBinding get() =_binding!!

    private var listAdapter:CategoriesAdapter?=null
    private var maxSelection = 3

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        listAdapter = CategoriesAdapter()
        binding.categoriesRecyclerview.adapter = listAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCategories()


        binding.submitBtn.setOnClickListener {
            validateCategorySelection()
        }
    }

    private fun getCategories() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.sourcesEvents.collect{ event->
                when(event){
                    is GetSourcesEvent.GetDataOnSuccess ->{
                        Log.i(TAG, "onViewCreated: sources size = ${event.sourceList.size}")

                        val categoryList = ListMapper.categoriesFromSources(event.sourceList)

                        updateUiListComponent(categoryList)
                    }
                    is GetSourcesEvent.ShowMessageOnError ->
                        Log.e(TAG, "onViewCreated: fail to load sources ${event.msg}")

                    else -> Log.d(TAG, "onViewCreated: show loading")
                }
            }
        }

    }





    private fun validateCategorySelection(){
        val selectedCategoryList = listAdapter?.getSelectedItems()

        if (selectedCategoryList?.size!! <= maxSelection)
            navigateToConfirmationDialogPage(selectedCategoryList)
        else
            Toast.makeText(context, getString(R.string.select_limit_error), Toast.LENGTH_SHORT).show()

    }

    private fun navigateToConfirmationDialogPage(selectedCategoryList: List<Category>) {

        val message = getString(R.string.select_categories_confirm)

        val action = CategoriesFragmentDirections.actionCategoriesFragmentToConfirmationDialogFragment(
            message, "category"
        )

        findNavController().navigate(action)
    }

    private fun updateUiListComponent(categoryList: List<Category>) {
        binding.categoriesRecyclerview.apply {
            if (adapter==null)
                adapter = listAdapter

            listAdapter?.submitList(categoryList)

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}