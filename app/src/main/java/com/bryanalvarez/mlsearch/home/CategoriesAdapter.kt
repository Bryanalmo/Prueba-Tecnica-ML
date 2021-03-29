package com.bryanalvarez.mlsearch.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bryanalvarez.domain.models.Category
import com.bryanalvarez.mlsearch.R
import com.bryanalvarez.mlsearch.databinding.SingleCategoryBinding

class CategoriesAdapter(private val onItemClick: (category: Category) -> Unit) :
    RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    private val list = mutableListOf<Category>()

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

        fun bind(category: Category) {
            binding.category = category
            binding.root.setOnClickListener { onItemClick(category) }
            binding.executePendingBindings()
        }


    }
}