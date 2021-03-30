package com.bryanalvarez.mlsearch.itemDetail

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bryanalvarez.domain.models.Item
import com.bryanalvarez.mlsearch.R
import com.bryanalvarez.mlsearch.databinding.FragmentItemDetailBinding
import com.bryanalvarez.mlsearch.results.ResultsAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_item_detail.*
import kotlinx.android.synthetic.main.fragment_results.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItemDetailFragment : DialogFragment() {

    private lateinit var binding: FragmentItemDetailBinding
    private val viewModel: ItemDetailViewModel by viewModel<ItemDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_detail, container, false)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolBarItemDetail.navigationIcon= ContextCompat.getDrawable(view.context, R.drawable.ic_back)
            toolBarItemDetail.setNavigationOnClickListener{
                dismiss()
            }
        }

        arguments?.getSerializable("itemSelected")?.let {item ->
            setupSellerPostsList(item as Item)
            setupItemAttributes(item)
            Glide.with(itemImage.context)
                .load(item.thumbnail)
                .into(itemImage)

            sellerNameText.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(viewModel.seller.permalink)
                startActivity(intent)
            }
        }

    }

    private fun setupItemAttributes(item: Item) {
        val adapter = ItemAttributesAdapter()
        itemInfoList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        itemInfoList.adapter = adapter
        itemInfoList.isNestedScrollingEnabled = false
        item.attributes?.let {
            adapter.updateList(it)
        }
    }

    private fun setupSellerPostsList(item: Item) {
        val adapter = ResultsAdapter{ item ->

        }
        sellerPostsList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        sellerPostsList.adapter = adapter
        sellerPostsList.isNestedScrollingEnabled = false
        sellerPostsList.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
        viewModel.getItemsBySeller(item).observe(this, Observer { list ->
            list.let {
                adapter.updateList(it)
            }
        })
    }
}