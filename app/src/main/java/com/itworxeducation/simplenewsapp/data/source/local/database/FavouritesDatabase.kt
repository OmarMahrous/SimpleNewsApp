package com.itworxeducation.simplenewsapp.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.itworxeducation.simplenewsapp.data.model.Article
import com.itworxeducation.simplenewsapp.data.model.sources.Category
import com.itworxeducation.simplenewsapp.data.source.local.database.favourite.articles.ArticleDao
import com.itworxeducation.simplenewsapp.data.source.local.database.favourite.onboarding.SourceDao

@Database(entities = [Category::class, Article::class], version = 9)
abstract class FavouritesDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    abstract fun sourceDao(): SourceDao

}