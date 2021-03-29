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
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bryanalvarez.mlsearch.R
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : DialogFragment() {

    private val viewModel: SearchViewModel by viewModel<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbarSearch.navigationIcon= ContextCompat.getDrawable(view.context, R.drawable.ic_back)
            toolbarSearch.setNavigationOnClickListener{
                dismiss()
            }
        }

        setupRecentSearch()

        editTextSearch.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                goToResults(textView.text.toString())
                viewModel.addUserSearch(textView.text.toString())
                hideKeyboard()
                true
            }
            false
        }
    }

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

    private fun goToResults(text: String?) {
        var args = Bundle()
        args.putString("searchText", text)
        findNavController().navigate(R.id.action_searchFragment_to_resultsFragment, args)
    }

    private fun hideKeyboard(){
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}