package com.itworxeducation.simplenewsapp.ui.onboarding_sources.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itworxeducation.simplenewsapp.R
import com.itworxeducation.simplenewsapp.data.model.sources.Category
import com.itworxeducation.simplenewsapp.databinding.CategoryListItemBinding
import com.itworxeducation.simplenewsapp.databinding.FavCategoryListItemBinding

class CategoriesAdapter(val isFavourite: Boolean) : ListAdapter<Category, RecyclerView.ViewHolder>(DiffCallback()) {

    private  val TAG = "CategoriesAdapter"



    inner class CategoriesViewHolder(val binding: CategoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {

            }
        }

        fun bind(category: Category, position: Int) {
            binding.apply {
                categoryName.text = category.name
                category.image?.let { categoryImage.setImageResource(it) }

                categoryCardView.setOnClickListener {
                    updateSelectedView(this,position)
                }

                categoryName.setOnClickListener {
                    updateSelectedView(this,position)
                }

                executePendingBindings()
            }
        }


    }

    inner class FavouriteCategoriesViewHolder(val binding: FavCategoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bindFav(category: Category) {
            binding.apply {
                categoryName.text = category.name

                executePendingBindings()
            }
        }


    }



    private fun updateSelectedView(binding: CategoryListItemBinding, position: Int) {
        val category = getItem(position)

        category.isSelected = !category.isSelected!!

        if (category.isSelected!!)
            binding.categoryImage.setImageResource(R.drawable.ic_selected)
        else
            category.image?.let { binding.categoryImage.setImageResource(it) }

    }

    fun getSelectedItems():List<Category>{
        val selectedList = mutableListOf<Category>()

        for (category in currentList){
            if (category.isSelected == true)
                selectedList.add(category)
        }
        return selectedList.toList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var viewHolder:RecyclerView.ViewHolder?=null

        if (isFavourite){
            val binding =
                FavCategoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            viewHolder = FavouriteCategoriesViewHolder(binding)
        }else{
            val binding =
                CategoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            viewHolder = CategoriesViewHolder(binding)
        }



        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (isFavourite) {

            val currentFavItem = getItem(position)
            (holder as FavouriteCategoriesViewHolder).bindFav(currentFavItem)

        }else{
            val currentItem = getItem(position)
            (holder as CategoriesViewHolder).bind(currentItem, position)
        }

    }



    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    class DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }



}

