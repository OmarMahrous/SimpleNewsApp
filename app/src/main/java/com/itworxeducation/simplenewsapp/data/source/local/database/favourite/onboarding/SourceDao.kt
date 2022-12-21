package com.itworxeducation.simplenewsapp.data.source.local.database.favourite.onboarding

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.itworxeducation.simplenewsapp.data.model.sources.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface SourceDao {

    @Query("INSERT INTO category_table (CountryName) VALUES(:countryName)")
    suspend fun addCountryToFavourites(countryName:String)

    @Query("SELECT CountryName FROM category_table")
    fun getFavouriteCountry():Flow<String>



    @Insert
    suspend fun addCategoryToFavourites(category: Category)

    @Query("SELECT * FROM category_table")
    fun getFavouriteCategories():Flow<List<Category>>

}