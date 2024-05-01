package com.dossantos.melimobilecandidate.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dossantos.melimobilecandidate.R
import com.dossantos.melimobilecandidate.databinding.FragmentSearchBinding
import com.dossantos.melimobilecandidate.ui.base.BaseFragment
import com.dossantos.melimobilecandidate.ui.home.SearchInterface
import com.dossantos.melimobilecandidate.utils.Integers.one
import com.dossantos.melimobilecandidate.viewmodel.search.SearchStateUi
import com.dossantos.melimobilecandidate.viewmodel.search.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val searchViewmodel by viewModel<SearchViewModel>()

    private var newStringSearch = MutableLiveData<String>()

    private val searchAdapter = SearchAdapter(mutableListOf(), ::setOnProductClickListener)

    private var hasPossibilityToRetry = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObservables()
        setupErrorScreen()
        setupInfinityScroll()
        newStringSearch.postValue(arguments?.getString(STRING_SEARCH, ""))
    }

    private fun setupObservables() {
        searchViewmodel.retryPossibility.observe(viewLifecycleOwner) { hasPossibility ->
            hasPossibilityToRetry = hasPossibility
        }

        searchViewmodel.searchStateUi.observe(viewLifecycleOwner) { uiState ->
            uiState.uiState?.let { onObservable(it) }
        }

        newStringSearch.observe(viewLifecycleOwner) { string ->
            searchAdapter.clear()
            searchViewmodel.initSearch(string)
        }
    }

    private fun setupErrorScreen() = with(binding.errorLayout) {
        retryButton.text =
            if (hasPossibilityToRetry) getString(R.string.search_again)
            else getString(R.string.back)

        retryButton.setOnClickListener {
            if (hasPossibilityToRetry) searchViewmodel.retrySearch()
            else findNavController().popBackStack()
        }
    }

    private fun setupInfinityScroll() = with(binding.recyclerView) {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = layoutManager as GridLayoutManager
                val totalItemsCount = layoutManager.itemCount
                val lastVisible = layoutManager.findLastVisibleItemPosition()

                if (lastVisible == totalItemsCount - one) searchViewmodel.tryNextPage()
            }
        })
    }

    private fun onObservable(stateUi: SearchStateUi.StateUi) = when (stateUi) {
        is SearchStateUi.StateUi.OnSuccess -> {
            binding.errorLayout.root.isVisible = false
            binding.loadingLayout.root.isVisible = false
            searchAdapter.updateList(stateUi.products)
        }

        is SearchStateUi.StateUi.ShowLoading -> {
            binding.errorLayout.root.isVisible = false
            binding.loadingLayout.root.isVisible = true
        }

        is SearchStateUi.StateUi.OnError -> {
            binding.loadingLayout.root.isVisible = false
            binding.errorLayout.root.isVisible = true
        }
    }

    private fun setupViews() {
        (requireActivity() as? SearchInterface)?.getToolbarView()?.let { toolbar ->
            toolbar.onSearch { newStringSearch.postValue(it) }
            toolbar.setup(
                backButtonVisibility = true,
                cancelButtonVisibility = false,
                cartButtonVisibility = true,
                isFixed = true
            )
            toolbar.setOnBackButtonClickListener {
                toolbar.setup(isDefaultSetup = true)
                toolbar.clearTextField()
                findNavController().popBackStack()
            }
        }
        binding.recyclerView.adapter = searchAdapter
    }


    private fun setOnProductClickListener(itemId: String) {
        println(itemId)
    }

    companion object {
        const val STRING_SEARCH = "STRING_SEARCH"
    }
}
