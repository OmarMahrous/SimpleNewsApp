package com.itworxeducation.simplenewsapp.ui.articles

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itworxeducation.simplenewsapp.R
import com.itworxeducation.simplenewsapp.data.model.Article
import com.itworxeducation.simplenewsapp.databinding.ArticleListItemBinding
import com.itworxeducation.simplenewsapp.ui.util.UrlUtil
import com.squareup.picasso.Picasso

class ArticlesAdapter(
    val context: Context
): ListAdapter<Article, ArticlesAdapter.ArticlesViewHolder>(DiffCallback()) {

    private  val TAG = "ArticlesAdapter"

//    private var isFavourite = false

    inner class ArticlesViewHolder(val binding: ArticleListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(article: Article) {
            binding.apply {
                this.article = article

                try {
                    Picasso.get()

                        .load(article.urlToImage)
                        .placeholder(R.mipmap.ic_placeholder)
                        .into(articleImage)
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

        var isFavourite=false
        holder.binding.saveArticle.setOnClickListener {
            isFavourite = !isFavourite

            updateSaveButtonIcon(isFavourite, holder.binding.saveArticle)

        }
    }

    private fun updateSaveButtonIcon(isFavourite: Boolean, saveArticle: ImageView) {

        if (isFavourite){
            saveArticle.setImageResource(R.drawable.ic_action_bookmark)
        }else {
            saveArticle.setImageResource(R.drawable.ic_action_unbookmark)
        }

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    class DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.source.sid == newItem.source.sid
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }



}