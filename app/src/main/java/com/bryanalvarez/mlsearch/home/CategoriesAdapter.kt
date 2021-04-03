package com.bryanalvarez.mlsearch.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bryanalvarez.domain.models.Category
import com.bryanalvarez.mlsearch.R
import com.bryanalvarez.mlsearch.databinding.SingleCategoryBinding

/**
 * Adapter to specify how to display each category
 */
class CategoriesAdapter(private val onItemClick: (category: Category) -> Unit) :
    RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    private val list = mutableListOf<Category>()

    /**
     * function to update the list after the adapter is initialized
     * @param newList the list updated
     */
    fun updateList(newList: List<Category>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<SingleCategoryBinding>(
            inflater,
            R.layout.single_category,
            parent,
            false
        )
        return CategoriesViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class CategoriesViewHolder(private val binding: SingleCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * function to bind the category info into the view
         * @param category category to be displayed
         */
        fun bind(category: Category) {
            binding.category = category
            binding.root.setOnClickListener { onItemClick(category) }
            binding.executePendingBindings()
        }


    }
}