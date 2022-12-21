package com.itworxeducation.simplenewsapp.data.model.sources

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category(
    val id:String, val name:String, val image:Int, var isSelected:Boolean
    ,@PrimaryKey(autoGenerate = true) val catId: Int

, @ColumnInfo(name = "CountryName") val countryName:String
)