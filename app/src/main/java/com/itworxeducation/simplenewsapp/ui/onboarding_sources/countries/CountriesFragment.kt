package com.itworxeducation.simplenewsapp.ui.onboarding_sources.countries

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.itworxeducation.simplenewsapp.R
import com.itworxeducation.simplenewsapp.databinding.FragmentCountriesBinding
import com.itworxeducation.simplenewsapp.ui.BaseFragment
import com.itworxeducation.simplenewsapp.ui.onboarding_sources.GetSourcesEvent
import com.itworxeducation.simplenewsapp.ui.onboarding_sources.SourcesViewModel
import com.itworxeducation.simplenewsapp.ui.util.ListMapper


class CountriesFragment: BaseFragment(R.layout.fragment_countries) {

    private val TAG = "CountriesFragment"

    private val viewModel: SourcesViewModel by activityViewModels()


    private var _binding: FragmentCountriesBinding? =null
    private val binding: FragmentCountriesBinding get() =_binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCountriesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCountries()

        binding.submitBtn.setOnClickListener {
            validateCountrySelection()
        }
    }


    private fun getCountries() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.sourcesEvents.collect{ event->
                when(event){
                    is GetSourcesEvent.GetDataOnSuccess ->{
                        Log.i(TAG, "onViewCreated: sources size = ${event.sourceList.size}")

                        val countryList = ListMapper.countriesFromSources(event.sourceList)

                        updateDropdownUiComponent(countryList)
                    }
                    is GetSourcesEvent.ShowMessageOnError ->
                        Log.e(TAG, "onViewCreated: fail to load sources ${event.msg}")

                    else -> Log.d(TAG, "onViewCreated: show loading")
                }
            }
        }

    }

    private fun validateCountrySelection(){
        val selectedCountry = binding.countriesAutocompleteTextview.text.toString()

        if (selectedCountry.isEmpty()){
            showFieldErrorMessage(getString(R.string.empty_country_selection_error))
            return
        }

        navigateToCategoriesPage(selectedCountry)
    }

    private fun navigateToCategoriesPage(selectedCountry: String) {

        val message = getString(R.string.select_country_confirm) + " $selectedCountry ?"

        val action = CountriesFragmentDirections.actionCountriesFragmentToConfirmationDialogFragment(
            message, "country"
        )

        findNavController().navigate(action)
    }




    private fun updateDropdownUiComponent(countryList: List<String>) {
        val adapter: ArrayAdapter<String>? = context?.let {
            ArrayAdapter<String>(
                it,
                android.R.layout.simple_spinner_dropdown_item,
                countryList
            )
        }

        binding.countriesAutocompleteTextview.setAdapter(adapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}