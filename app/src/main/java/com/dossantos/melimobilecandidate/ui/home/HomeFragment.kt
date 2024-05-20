package com.dossantos.melimobilecandidate.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.dossantos.designsystem.model.suggestions.MeLiProducts
import com.dossantos.domain.model.suggestions.SuggestionsType
import com.dossantos.melimobilecandidate.R
import com.dossantos.melimobilecandidate.databinding.FragmentHomeBinding
import com.dossantos.melimobilecandidate.ui.base.BaseFragment
import com.dossantos.melimobilecandidate.ui.product.ProductDetailFragment
import com.dossantos.melimobilecandidate.ui.search.SearchFragment
import com.dossantos.melimobilecandidate.utils.ElseNothing
import com.dossantos.melimobilecandidate.utils.homeToDetail
import com.dossantos.melimobilecandidate.utils.homeToSearch
import com.dossantos.melimobilecandidate.viewmodel.home.CategoryMenuUiState
import com.dossantos.melimobilecandidate.viewmodel.home.HomeViewModel
import com.dossantos.melimobilecandidate.viewmodel.home.OfferUiState
import com.dossantos.melimobilecandidate.viewmodel.home.SuggestionsUiState
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val _viewModel: HomeViewModel by activityViewModel()
    val viewModel: HomeViewModel
        get() = _viewModel

    private val searchString = MutableLiveData<String>()

    private var lastSearch = String()

    private val objectDetailSelected = MutableLiveData<String>()

    private var lastObjectDetailSelected = String()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewModel.init()
        setupObservables()
        setupOnSearch()
        clearSearch()
    }

    private fun clearSearch() {
        objectDetailSelected.value = String()
        searchString.value = String()
        lastSearch = String()
        lastObjectDetailSelected = String()
    }

    private fun setupOnSearch() {
        (activity as? SearchInterface)?.getToolbarView()?.onSearch {
            searchString.postValue(it)
        }
    }

    private fun setupObservables() {
        _viewModel.offerUiState.observe(viewLifecycleOwner, ::onOffers)
        _viewModel.categoryMenuUiState.observe(viewLifecycleOwner, ::onCategoryMenu)
        _viewModel.suggestionsUiState.observe(viewLifecycleOwner, ::onSuggestions)

        searchString.observe(viewLifecycleOwner) { string ->
            if (string.isNotEmpty() && string != lastSearch) {
                lastSearch = string
                findNavController().homeToSearch(bundleOf(SearchFragment.STRING_SEARCH to string))
            }
        }

        objectDetailSelected.observe(viewLifecycleOwner) { string ->
            if (string.isNotEmpty() && string != lastObjectDetailSelected) {
                lastObjectDetailSelected = string
                findNavController().homeToDetail(bundleOf(ProductDetailFragment.SEARCH_PRODUCT to string))
            }
        }
    }

    private fun onOffers(observable: OfferUiState) = when (val uiState = observable.uiState) {
        is OfferUiState.StateUi.OnSuccess -> {
            activity?.let { binding.meLiOfferCarousel.setup(uiState.offers, it) }
            binding.meLiOfferCarousel.setOnCardClickListener { searchString.postValue(it) }
        }

        is OfferUiState.StateUi.OnError -> {
            _viewModel.retryOffer()
        }

        else -> ElseNothing
    }

    private fun onCategoryMenu(observable: CategoryMenuUiState) =
        when (val uiState = observable.uiState) {
            is CategoryMenuUiState.StateUi.OnSuccess -> {
                binding.categoryLoading.isVisible = false
                binding.categoryLayout.isVisible = true
                binding.meLiCategoryCarousel.setup(uiState.categories)
                { searchString.postValue(it) }
            }

            is CategoryMenuUiState.StateUi.OnError -> {
                binding.categoryLayout.isVisible = false
                _viewModel.retryCategoryMenu {
                    binding.categoryLoading.isVisible = false
                }
            }

            is CategoryMenuUiState.StateUi.ShowLoading -> {
                binding.categoryLoading.isVisible = true
            }

            else -> ElseNothing
        }

    private fun onSuggestions(observable: SuggestionsUiState) =
        when (val uiState = observable.uiState) {
            is SuggestionsUiState.StateUi.OnSuccess -> {
                binding.suggestionsRecyclerView.adapter =
                    HomeSuggestionsAdapter(uiState.suggestions.convertTexts())
                    { objectDetailSelected.postValue(it) }
            }

            is SuggestionsUiState.StateUi.OnError -> {
                _viewModel.retrySuggestions()
            }

            else -> ElseNothing
        }

    private fun List<Pair<SuggestionsType, List<MeLiProducts>?>>.convertTexts() = map { pair ->
        pair.first.toSuggestionTitle() to pair.second
    }

    private fun SuggestionsType.toSuggestionTitle() = when (this) {
        SuggestionsType.ONLY_SUGGESTION -> activity?.getString(R.string.suggestion_message)
        SuggestionsType.ALREADY_VISITED -> activity?.getString(R.string.already_suggestion_message)
    }
}
