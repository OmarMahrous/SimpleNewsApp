package com.itworxeducation.simplenewsapp.data.source.local.database.favourite.onboarding

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itworxeducation.simplenewsapp.data.model.sources.Category
import com.itworxeducation.simplenewsapp.data.model.sources.Country
import kotlinx.coroutines.flow.Flow

@Dao
interface SourceDao {

    @Insert
    suspend fun addCountryToFavourites(country:Country)

    @Query("SELECT name FROM country_table")
    fun getFavouriteCountry():String


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCategoryToFavourites(categoryList: List<Category>)


    @Query("SELECT * FROM category_table") // WHERE name = 'sports'
    fun getFavouriteCategories(): Flow<List<Category>>

}