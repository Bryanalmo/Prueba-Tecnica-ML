package com.bryanalvarez.mlsearch.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bryanalvarez.domain.models.UserSearch
import com.bryanalvarez.mlsearch.R
import com.bryanalvarez.mlsearch.databinding.SingleRecentSearchBinding

/**
 * Adapter to specify how to display each item search
 */
class RecentSearchAdapter(private val onItemClick: (userSearch: UserSearch) -> Unit) : RecyclerView.Adapter<RecentSearchAdapter.RecentSearchViewHolder>() {

    private val list = mutableListOf<UserSearch>()

    /**
     * function to update the list after the adapter is initialized
     * @param newList the list updated
     */
    fun updateList(newList: List<UserSearch>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<SingleRecentSearchBinding>(
            inflater,
            R.layout.single_recent_search,
            parent,
            false
        )
        return RecentSearchViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecentSearchViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class RecentSearchViewHolder(private val binding: SingleRecentSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * function to bind the userSearch info into the view
         * @param userSearch userSearch to be displayed
         */
        fun bind(userSearch: UserSearch) {

            binding.userSearch = userSearch
            binding.root.setOnClickListener { onItemClick(userSearch) }
            binding.executePendingBindings()
        }


    }
}