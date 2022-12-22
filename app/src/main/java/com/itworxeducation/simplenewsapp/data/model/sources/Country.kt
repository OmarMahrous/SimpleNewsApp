package com.itworxeducation.simplenewsapp.data.model.sources

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country_table")
data class Country(val name:String
                   ,@PrimaryKey(autoGenerate = true) val id: Int=0)
