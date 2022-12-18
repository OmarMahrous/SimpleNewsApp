package com.itworxeducation.simplenewsapp.ui.onboarding_sources.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itworxeducation.simplenewsapp.data.model.Category
import com.itworxeducation.simplenewsapp.databinding.CategoryListItemBinding

class CategoriesAdapter(
    var categoryList:List<Category>,
    private val listener: IOnItemClickListener
    ) : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    inner class CategoriesViewHolder(val binding: CategoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {

//                root.setOnClickListener {
//                    val position = adapterPosition
//                    if (position != RecyclerView.NO_POSITION) {
//                        val task = getItem(position)
//                        listener.onItemClick(task)
//                    }
//                }

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val binding =
            CategoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CategoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val currentItem = categoryList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }



    fun submitNewData(newList: List<Category>) {

        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return categoryList[oldItemPosition] == newList[newItemPosition]
            }

            override fun getOldListSize(): Int {
                return categoryList.size
            }

            override fun getNewListSize(): Int {
                return newList.size
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return categoryList[oldItemPosition] == newList[newItemPosition]
            }
        })
        categoryList = newList.toMutableList()

        diffResult.dispatchUpdatesTo(this)


    }

    interface IOnItemClickListener {
        fun onItemClick(category: Category)
    }

}