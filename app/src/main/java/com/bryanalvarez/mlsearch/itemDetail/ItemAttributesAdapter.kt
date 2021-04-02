package com.bryanalvarez.mlsearch.itemDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bryanalvarez.domain.models.Attribute
import com.bryanalvarez.domain.models.Item
import com.bryanalvarez.mlsearch.R
import com.bryanalvarez.mlsearch.databinding.SingleItemAttributeBinding
import com.bryanalvarez.mlsearch.databinding.SingleItemListBinding
import com.bumptech.glide.Glide

/**
 * Adapter to specify how to display each item attribute
 */
class ItemAttributesAdapter : RecyclerView.Adapter<ItemAttributesAdapter.ItemAttributesViewHolder>() {

    private val list = mutableListOf<Attribute>()

    /**
     * function to update the list after the adapter is initialized
     * @param newList the list updated
     */
    fun updateList(newList: List<Attribute>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAttributesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<SingleItemAttributeBinding>(
            inflater,
            R.layout.single_item_attribute,
            parent,
            false
        )
        return ItemAttributesViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ItemAttributesViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ItemAttributesViewHolder(private val binding: SingleItemAttributeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * function to bind the attribute info into the view
         * @param attribute attribute to be displayed
         */
        fun bind(attribute: Attribute) {
            binding.attribute = attribute
            binding.executePendingBindings()
        }


    }
}