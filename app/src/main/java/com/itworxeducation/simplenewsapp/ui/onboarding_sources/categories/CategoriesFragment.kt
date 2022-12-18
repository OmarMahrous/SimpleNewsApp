package com.itworxeducation.simplenewsapp.ui.onboarding_sources.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.itworxeducation.simplenewsapp.R
import com.itworxeducation.simplenewsapp.data.model.Category
import com.itworxeducation.simplenewsapp.databinding.FragmentCategoriesBinding
import com.itworxeducation.simplenewsapp.ui.BaseFragment
import com.itworxeducation.simplenewsapp.ui.onboarding_sources.GetSourcesEvent
import com.itworxeducation.simplenewsapp.ui.onboarding_sources.SourcesViewModel
import com.itworxeducation.simplenewsapp.ui.onboarding_sources.countries.CountriesFragmentDirections
import com.itworxeducation.simplenewsapp.ui.util.ListMapper

class CategoriesFragment: BaseFragment(R.layout.fragment_categories),
    CategoriesAdapter.IOnItemClickListener {

    private val TAG = "CategoriesFragment"

    private val viewModel: SourcesViewModel by activityViewModels()


    private var _binding: FragmentCategoriesBinding? =null
    private val binding: FragmentCategoriesBinding get() =_binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCountries()

        binding.submitBtn.setOnClickListener {
//            validateCountrySelection()
        }
    }


    private fun getCountries() {
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

//    private fun validateCountrySelection(){
//        val selectedCountry = binding.countriesAutocompleteTextview.text.toString()
//
//        if (selectedCountry.isEmpty()){
//            showFieldErrorMessage("Please select your country !")
//            return
//        }
//
//        navigateToArticlesPage(selectedCountry)
//    }

    private fun navigateToArticlesPage(selectedCountry: String) {

        val message = getString(R.string.select_country_confirm) + " $selectedCountry ?"

        val action = CountriesFragmentDirections.actionCountriesFragmentToConfirmationDialogFragment(
            message, "country"
        )

        findNavController().navigate(action)
    }




    private fun updateUiListComponent(categoryList: List<Category>) {
        Log.d(TAG, "updateUiListComponent: list size = ${categoryList.size}")

        binding.categoriesRecyclerview.apply {
            if (adapter==null)
                adapter = CategoriesAdapter(categoryList, this@CategoriesFragment)

            (adapter as CategoriesAdapter).submitNewData(categoryList)

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(category: Category) {

    }
}