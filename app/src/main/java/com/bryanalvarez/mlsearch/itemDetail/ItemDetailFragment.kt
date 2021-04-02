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
import com.bryanalvarez.mlsearch.MainActivity
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
            setupToolBar(view)
        }

        arguments?.getSerializable("itemSelected")?.let {item ->
            setupSellerPostsList(item as Item)
            setupItemAttributes(item)
            Glide.with(itemImage.context)
                .load(item.thumbnail)
                .into(itemImage)

            sellerNameText.setOnClickListener {
                goToSellerProfile()
            }
        }

        viewModel.itemBySellerError.observe(this, Observer {
            (activity as MainActivity).showError(itemDetailContainer,it)
        })

    }

    /**
     * function to set the toolbar's navigation icon and onclick listener
     */
    private fun setupToolBar(view: View){
        toolBarItemDetail.navigationIcon= ContextCompat.getDrawable(view.context, R.drawable.ic_back)
        toolBarItemDetail.setNavigationOnClickListener{
            dismiss()
        }
    }

    /**
     * function to setup the item attributes list
     * instantiate the ItemAttributesAdapter and set the list from the item selected
     * @param item item selected
     */
    private fun setupItemAttributes(item: Item) {
        val adapter = ItemAttributesAdapter()
        itemInfoList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        itemInfoList.adapter = adapter
        itemInfoList.isNestedScrollingEnabled = false
        item.attributes?.let {
            adapter.updateList(it)
        }
    }

    /**
     * function to setup the seller posts list
     * instantiate the ResultsAdapter and observes the list from the viewModel
     */
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

    /**
     * function to redirect the user to the seller profile in the browser
     */
    private fun goToSellerProfile(){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(viewModel.seller.permalink)
        startActivity(intent)
    }
}