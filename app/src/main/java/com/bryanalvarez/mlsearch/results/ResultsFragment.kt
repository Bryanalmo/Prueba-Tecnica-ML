package com.bryanalvarez.mlsearch.results

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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
            setupItemList(true)
        }

        arguments?.getSerializable("category")?.let {
            viewModel.categorySelected = it as Category
            setupItemList(false)
        }

        viewModel.notifyChange()

        searchResults.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }

        viewModel.itemsResultsError.observe(this, Observer {
            (activity as MainActivity).showError(resultsContainer,it)
        })
    }

    private fun setupItemList(bySearch: Boolean){
        val adapter = ResultsAdapter{ item ->
            goToItemDetail(item)
        }
        resultsList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        resultsList.adapter = adapter
        resultsList.isNestedScrollingEnabled = false
        resultsList.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
        viewModel.getItemsList(bySearch).observe(this, Observer { list ->
            list.let {
                adapter.updateList(it)
            }
        })
    }

    private fun goToItemDetail(item: Item) {
        var args = Bundle()
        args.putSerializable("itemSelected", item)
        findNavController().navigate(R.id.action_resultsFragment_to_itemDetailFragment, args)
    }
}