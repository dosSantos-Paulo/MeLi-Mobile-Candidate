package com.dossantos.melimobilecandidate.ui.home

import android.os.Bundle
import android.view.View
import com.dossantos.designsystem.model.category.MeLiCategory
import com.dossantos.designsystem.model.offer.MeLiOffer
import com.dossantos.designsystem.model.suggestions.MeLiSuggestion
import com.dossantos.domain.model.suggestions.SuggestionsType
import com.dossantos.melimobilecandidate.R
import com.dossantos.melimobilecandidate.databinding.FragmentHomeBinding
import com.dossantos.melimobilecandidate.ui.base.BaseFragment
import com.dossantos.melimobilecandidate.utils.ElseNothing
import com.dossantos.melimobilecandidate.viewmodel.home.CategoryMenuUiState
import com.dossantos.melimobilecandidate.viewmodel.home.HomeViewModel
import com.dossantos.melimobilecandidate.viewmodel.home.OfferUiState
import com.dossantos.melimobilecandidate.viewmodel.home.SuggestionsUiState
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by activityViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
        observeOffers()
        observeCategoryMenu()
        observeSuggestions()
    }

    private fun observeOffers() =
        viewModel.offerUiState.observe(viewLifecycleOwner, ::onOffers)

    private fun observeCategoryMenu() =
        viewModel.categoryMenuUiState.observe(viewLifecycleOwner, ::onCategoryMenu)

    private fun observeSuggestions() =
        viewModel.suggestionsUiState.observe(viewLifecycleOwner, ::onSuggestions)

    private fun onOffers(observable: OfferUiState) = when (val uiState = observable.uiState) {
        is OfferUiState.StateUi.OnSuccess -> showOffers(uiState.offers)
        is OfferUiState.StateUi.OnError -> viewModel.retryOffer()
        else -> ElseNothing
    }

    private fun onCategoryMenu(observable: CategoryMenuUiState) =
        when (val uiState = observable.uiState) {
            is CategoryMenuUiState.StateUi.OnSuccess -> showMenu(uiState.categories)
            is CategoryMenuUiState.StateUi.OnError -> viewModel.retryCategoryMenu()
            else -> ElseNothing
        }

    private fun onSuggestions(observable: SuggestionsUiState) =
        when (val uiState = observable.uiState) {
            is SuggestionsUiState.StateUi.OnSuccess -> showSuggestions(uiState.suggestions)
            is SuggestionsUiState.StateUi.OnError -> viewModel.retrySuggestions()
            else -> ElseNothing
        }

    private fun showOffers(offers: List<MeLiOffer>) = binding.meLiOfferCarousel
        .setup(offers, requireActivity())

    private fun showMenu(categories: List<MeLiCategory>) = binding.meLiCategoryCarousel
        .setup(categories)

    private fun showSuggestions(suggestions: List<Pair<SuggestionsType, List<MeLiSuggestion>?>>) {
        binding.suggestionsRecyclerView.adapter = HomeSuggestionsAdapter(suggestions.convertTexts())
    }

    private fun List<Pair<SuggestionsType, List<MeLiSuggestion>?>>.convertTexts() = map { pair ->
        pair.first.toSuggestionTitle() to pair.second
    }

    private fun SuggestionsType.toSuggestionTitle() = when (this) {
        SuggestionsType.ONLY_SUGGESTION ->
            requireActivity().getString(R.string.suggestion_message)

        SuggestionsType.ALREADY_VISITED ->
            requireActivity().getString(R.string.already_suggestion_message)
    }
}
