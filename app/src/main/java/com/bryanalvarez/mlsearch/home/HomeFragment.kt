package com.bryanalvarez.mlsearch.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bryanalvarez.domain.models.Category
import com.bryanalvarez.mlsearch.R
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCategories()

        search.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

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

    private fun goToResultsByCategory(category: Category){
        val bundle = Bundle()
        bundle.putSerializable("category", category)
        findNavController().navigate(R.id.action_homeFragment_to_resultsFragment, bundle)
    }
}