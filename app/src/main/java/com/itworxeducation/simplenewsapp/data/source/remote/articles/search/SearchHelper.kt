package com.itworxeducation.simplenewsapp.data.source.remote.articles.search

import com.itworxeducation.simplenewsapp.data.model.Article

class SearchHelper {
    companion object{

        fun searchArticle(input:String, articleList: List<Article>): List<Article> {
            val filteredList = articleList.filter { article -> article.description!!.contains(input)
                    || article.title!!.contains(input) || article.content!!.contains(input)
                    || article.source!!.sname?.contains(input) == true
            }

            return orderByDate(filteredList)
        }

        private fun orderByDate(articleList: List<Article>): List<Article> {
            return articleList.sortedBy { it.publishedAt }
        }

    }
}