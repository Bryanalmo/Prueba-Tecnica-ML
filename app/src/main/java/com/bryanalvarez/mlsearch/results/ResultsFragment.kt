package com.bryanalvarez.mlsearch.results

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bryanalvarez.domain.constants.PAGE_LIMIT
import com.bryanalvarez.domain.models.Category
import com.bryanalvarez.domain.models.Item
import com.bryanalvarez.mlsearch.MainActivity
import com.bryanalvarez.mlsearch.R
import com.bryanalvarez.mlsearch.databinding.FragmentResultsBinding
import kotlinx.android.synthetic.main.fragment_results.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResultsFragment : Fragment() {

    private lateinit var binding: FragmentResultsBinding
    private val viewModel: ResultsViewModel by viewModel<ResultsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString("searchText")?.let {
            viewModel.searchText = it
            viewModel.bySearch = true
            setupItemList()
        }

        arguments?.getSerializable("category")?.let {
            viewModel.categorySelected = it as Category
            viewModel.bySearch = false
            setupItemList()
        }

        viewModel.notifyChange()

        searchResults.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }

        viewModel.itemsResultsError.observe(this, Observer {
            (activity as MainActivity).showError(resultsContainer,it)
        })
    }

    private fun setupItemList(){
        val adapter = ResultsAdapter{ item ->
            goToItemDetail(item)
        }
        resultsList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        resultsList.adapter = adapter
        resultsList.isNestedScrollingEnabled = false
        resultsList.addOnScrollListener(scrollListener)
        resultsList.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
        viewModel.getItemsList().observe(this, Observer { list ->
            list.let {
                adapter.updateList(it.toList())
            }
        })
    }

    private fun goToItemDetail(item: Item) {
        var args = Bundle()
        args.putSerializable("itemSelected", item)
        findNavController().navigate(R.id.action_resultsFragment_to_itemDetailFragment, args)
    }

    var isLoading = false
    var isScrolling = false

    private val scrollListener = object: RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !viewModel.isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= PAGE_LIMIT - 2
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if(shouldPaginate && !viewModel.loadingPagingItemsList) {
                viewModel.getItemsList()
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }
    }
}