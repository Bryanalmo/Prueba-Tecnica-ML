package com.bryanalvarez.mlsearch.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bryanalvarez.domain.models.Category
import com.bryanalvarez.domain.models.Item
import com.bryanalvarez.mlsearch.MainActivity
import com.bryanalvarez.mlsearch.R
import com.bryanalvarez.mlsearch.databinding.FragmentHomeBinding
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_item_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCategories()
        viewModel.getLastSeenItem()

        search.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        viewModel.homeError.observe(this, Observer {
            (activity as MainActivity).showError(homeContainer,it)
        })

        viewModel.urlLastSeenItem.observe(this, Observer {
            Glide.with(lastSeenItemImage.context)
                .load(it)
                .into(lastSeenItemImage)
        })

        lastItemSeenCard.setOnClickListener {
            goToItemDetail()
        }
    }
    /**
     * function to setup the categories list
     * instantiate the CategoriesAdapter and observes the list from the viewModel
     */
    private fun setupCategories() {
        val adapter = CategoriesAdapter{ category ->
            goToResultsByCategory(category)
        }
        var layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        categoriesList.layoutManager = layoutManager
        categoriesList.adapter = adapter
        categoriesList.isNestedScrollingEnabled = false
        viewModel.getCategories().observe(this, Observer { list ->
            list.let {
                adapter.updateList(it)
            }
        })
    }

    /**
     * function to redirect the user to the results screen, it pass the category selected as an argument
     * @param category category selected to be explored
     */
    private fun goToResultsByCategory(category: Category){
        val bundle = Bundle()
        bundle.putSerializable("category", category)
        findNavController().navigate(R.id.action_homeFragment_to_resultsFragment, bundle)
    }

    /**
     * function to redirect the user to the item detail screen, it pass the item selected as an argument
     */
    private fun goToItemDetail() {
        var args = Bundle()
        args.putSerializable("itemSelected", viewModel.lastSeenItem)
        findNavController().navigate(R.id.action_homeFragment_to_itemDetailFragment, args)
    }
}