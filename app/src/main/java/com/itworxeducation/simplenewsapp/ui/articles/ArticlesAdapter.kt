package com.itworxeducation.simplenewsapp.ui.articles

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itworxeducation.simplenewsapp.R
import com.itworxeducation.simplenewsapp.data.model.Article
import com.itworxeducation.simplenewsapp.data.model.Category
import com.itworxeducation.simplenewsapp.databinding.ArticleListItemBinding
import com.itworxeducation.simplenewsapp.databinding.CategoryListItemBinding
import com.itworxeducation.simplenewsapp.ui.onboarding_sources.categories.CategoriesAdapter
import com.itworxeducation.simplenewsapp.ui.util.UrlUtil
import com.squareup.picasso.Picasso

class ArticlesAdapter(
    val context: Context
): ListAdapter<Article, ArticlesAdapter.ArticlesViewHolder>(DiffCallback()) {

    private  val TAG = "ArticlesAdapter"



    inner class ArticlesViewHolder(val binding: ArticleListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(article: Article) {
            binding.apply {
                this.article = article

                try {
                    Picasso.get()
                        .load(article.urlToImage).into(articleImage)
                }catch (e:Exception){
                    Log.e(TAG, "bind: error load image : ${e.message}" )
                }

                executePendingBindings()
            }
        }


    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val binding =
            ArticleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ArticlesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)

        holder.binding.articleCardView.setOnClickListener {
            currentItem?.let { UrlUtil.openWebPage(context, it.url) }

        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    class DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.source.id == newItem.source.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }



}