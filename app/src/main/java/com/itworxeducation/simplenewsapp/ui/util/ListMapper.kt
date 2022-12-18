package com.itworxeducation.simplenewsapp.ui.util

import com.itworxeducation.simplenewsapp.data.model.OnboardingSource

class ListMapper {

    companion object{
        fun countriesFromSources(sources:List<OnboardingSource>): List<String> {
            val countryList = mutableListOf<String>()

            for (source in sources){
                countryList.add(source.country)
            }

            val uniqueItems = countryList.toSet().toList() // remove duplication

            return uniqueItems
        }
    }

}