package com.itworxeducation.simplenewsapp.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.itworxeducation.simplenewsapp.data.model.sources.OnboardingSource

@Entity(tableName = "article_table")
data class Article(@Embedded val source: OnboardingSource,
                   val author:String,
                   val title:String,
                   val description:String,
                   val url:String,
                   val urlToImage:String,
                   val publishedAt:String,
                   val content:String,
                   @PrimaryKey(autoGenerate = true) val id: Int
                    )


