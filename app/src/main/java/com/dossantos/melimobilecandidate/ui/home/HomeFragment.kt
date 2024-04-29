package com.dossantos.melimobilecandidate.ui.home

import android.os.Bundle
import android.view.View
import com.dossantos.designsystem.model.category.MeLiCategory
import com.dossantos.designsystem.model.offer.MeLiOffer
import com.dossantos.designsystem.model.suggestions.MeLiSuggestion
import com.dossantos.melimobilecandidate.databinding.FragmentHomeBinding
import com.dossantos.melimobilecandidate.ui.base.BaseFragment
import com.dossantos.melimobilecandidate.ui.home.HomeViewModel.Companion.CategoryMenuUiState
import com.dossantos.melimobilecandidate.ui.home.HomeViewModel.Companion.OfferUiState
import com.dossantos.melimobilecandidate.utils.ElseNothing
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by activityViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
        observeOffers()
        observeCategoryMenu()
    }

    private fun observeOffers() =
        viewModel.offerUiState.observe(viewLifecycleOwner, ::onOffers)

    private fun observeCategoryMenu() =
        viewModel.categoryMenuUiState.observe(viewLifecycleOwner, ::onCategoryMenu)

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

    private fun showOffers(offers: List<MeLiOffer>) = binding.meLiOfferCarousel
        .setup(offers, requireActivity())

    private fun showMenu(categories: List<MeLiCategory>) = binding.meLiCategoryCarousel
        .setup(categories)
}
