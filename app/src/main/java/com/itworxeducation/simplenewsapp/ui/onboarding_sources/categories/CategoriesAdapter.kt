package com.itworxeducation.simplenewsapp.ui.onboarding_sources.categories

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itworxeducation.simplenewsapp.R
import com.itworxeducation.simplenewsapp.data.model.Category
import com.itworxeducation.simplenewsapp.databinding.CategoryListItemBinding

class CategoriesAdapter(
    ) : ListAdapter<Category, CategoriesAdapter.CategoriesViewHolder>(DiffCallback()) {

    private  val TAG = "CategoriesAdapter"



    inner class CategoriesViewHolder(val binding: CategoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {



//                completedCheckbox.setOnClickListener {
//                    val position = adapterPosition
//                    if (position != RecyclerView.NO_POSITION) {
//                        val task = getItem(position)
//                        listener.onCheckboxClick(task, completedCheckbox.isChecked)
//                    }
//                }

            }
        }

        fun bind(category: Category) {
            binding.apply {
                categoryName.text = category.name
                categoryImage.setImageResource(category.image)

                executePendingBindings()
            }
        }


    }



    private fun updateSelectedView(binding: CategoryListItemBinding, position: Int) {
        val category = getItem(position)

        category.isSelected = !category.isSelected

        if (category.isSelected)
            binding.categoryImage.setImageResource(R.drawable.ic_selected)
        else
            binding.categoryImage.setImageResource(category.image)

    }

    fun getSelectedItems():List<Category>{
        val selectedList = mutableListOf<Category>()

        for (category in currentList){
            if (category.isSelected)
                selectedList.add(category)
        }
        return selectedList.toList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val binding =
            CategoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CategoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)

        holder.itemView.setOnClickListener {
            updateSelectedView(holder.binding,position)
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

