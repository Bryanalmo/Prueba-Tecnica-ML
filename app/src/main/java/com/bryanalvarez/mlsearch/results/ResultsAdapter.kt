package com.bryanalvarez.mlsearch.results

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bryanalvarez.domain.models.Item
import com.bryanalvarez.mlsearch.R
import com.bryanalvarez.mlsearch.databinding.SingleItemListBinding
import com.bumptech.glide.Glide

class ResultsAdapter(private val onItemClick: (item: Item) -> Unit) :
    RecyclerView.Adapter<ResultsAdapter.ResultsListViewHolder>() {

    private val list = mutableListOf<Item>()

    fun updateList(newList: List<Item>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<SingleItemListBinding>(
            inflater,
            R.layout.single_item_list,
            parent,
            false
        )
        return ResultsListViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ResultsListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ResultsListViewHolder(private val binding: SingleItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {

            binding.item = item

            val moviePosterImage = binding.root.findViewById<ImageView>(R.id.itemImage)
            Glide.with(moviePosterImage.context)
                .load(item.thumbnail)
                .into(moviePosterImage)

            binding.root.setOnClickListener { onItemClick(item) }
            binding.executePendingBindings()
        }


    }
}