package com.itworxeducation.simplenewsapp.ui.util

import com.itworxeducation.simplenewsapp.R
import com.itworxeducation.simplenewsapp.data.model.sources.Category
import com.itworxeducation.simplenewsapp.data.model.sources.OnboardingSource

class ListMapper {

    companion object{
        fun countriesFromSources(sources:List<OnboardingSource>): List<String> {
            val countryList = mutableListOf<String>()

            for (source in sources){
                source.country?.let { countryList.add(it) }
            }

            val uniqueItems = countryList.toSet().toList() // remove duplication

            return uniqueItems
        }

        fun categoriesFromSources(sources:List<OnboardingSource>): List<Category> {
            val categoryList = mutableListOf<Category>()

            for (source in sources){
                val categoryImage = source.category?.let { createCustomImage(it) }
                val category = Category(source.sid, source.category, categoryImage, countryName = "",
                isSelected = false, catId = 0)
                categoryList.add(category)
            }

            val uniqueItems = categoryList.distinctBy { it.name } // remove duplication

            return uniqueItems
        }

        private fun createCustomImage(category: String): Int {
            return when(category){
                "business" -> R.drawable.ic_business
                "entertainment" -> R.drawable.ic_entertainment
                "health" -> R.drawable.ic_healthcare
                "science" -> R.drawable.ic_science
                "sports" -> R.drawable.ic_sports
                "technology" -> R.drawable.ic_technology
                else -> R.drawable.ic_general
            }
        }
    }

}