package com.itworxeducation.simplenewsapp.data.source.local.database.favourite.articles

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.itworxeducation.simplenewsapp.data.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao{

    @Insert
    suspend fun addArticleToFavourites(article: Article)

    @Delete
    suspend fun removeArticleFromFavourites(article: Article)

    fun getFavouritesArticles(searchQuery: String):Flow<List<Article>> =
        getArticlesSortedByDateCreated(searchQuery)

    @Query("SELECT * FROM article_table WHERE (title LIKE '%' || :searchQuery || '%' OR " +
            "description LIKE '%' || :searchQuery || '%'OR" +
            " author LIKE '%' || :searchQuery || '%') ORDER BY publishedAt")
     fun getArticlesSortedByDateCreated(searchQuery: String): Flow<List<Article>>
}