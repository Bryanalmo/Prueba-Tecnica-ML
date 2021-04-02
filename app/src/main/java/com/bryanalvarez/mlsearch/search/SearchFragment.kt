package com.bryanalvarez.mlsearch.search

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bryanalvarez.mlsearch.MainActivity
import com.bryanalvarez.mlsearch.R
import com.bryanalvarez.mlsearch.databinding.FragmentSearchBinding
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : DialogFragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModel<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupToolBar(view)
        }

        setupRecentSearch()

        editTextSearch.setOnEditorActionListener { textView, actionId, keyEvent ->
            handleSearchButtonInKeyboard(actionId, textView)
        }

        viewModel.recentSearchesError.observe(this, Observer {
            (activity as MainActivity).showError(searchContainer,it)
        })
    }

    /**
     * function to set the toolbar's navigation icon and onclick listener
     */
    private fun setupToolBar(view: View){
        toolbarSearch.navigationIcon= ContextCompat.getDrawable(view.context, R.drawable.ic_back)
        toolbarSearch.setNavigationOnClickListener{
            dismiss()
        }
    }

    /**
     * function to setup the recent searches list
     * instantiate the RecentSearchAdapter and observes the list from the viewModel
     */
    private fun setupRecentSearch() {
        val adapter = RecentSearchAdapter(){
            goToResults(it.textSearched)
        }
        recentSearchList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recentSearchList.adapter = adapter
        recentSearchList.isNestedScrollingEnabled = false
        viewModel.getUserSearchList().observe(this, Observer { list ->
            list.let {
                adapter.updateList(it)
            }
        })
    }

    /**
     * function to redirect the user to the results screen, it pass the search as an argument
     * @param text search typed by the user
     */
    private fun goToResults(text: String?) {
        var args = Bundle()
        args.putString("searchText", text)
        findNavController().navigate(R.id.action_searchFragment_to_resultsFragment, args)
    }

    /**
     * function to detect when the user press the search button in the keyboard
     * @param actionId action sent by the button
     * @param textView
     */
    private fun handleSearchButtonInKeyboard(actionId: Int, textView: TextView): Boolean{
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            goToResults(textView.text.toString())
            viewModel.addUserSearch(textView.text.toString())
            hideKeyboard()
            return true
        }
        return false
    }

    /**
     * function to hide the keyboard
     */
    private fun hideKeyboard(){
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}