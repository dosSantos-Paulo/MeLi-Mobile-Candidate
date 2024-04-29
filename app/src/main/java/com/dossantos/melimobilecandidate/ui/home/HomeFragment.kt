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
        binding.suggestionsRecyclerView.adapter = HomeSuggestionsAdapter(
            listOf(mockSuggestions(), mockSuggestions(), mockSuggestions())
        )
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

    private fun mockSuggestions(): Pair<String, List<MeLiSuggestion>> {
        return "Porque Você viu items de Papelaria" to
                listOf(
                    MeLiSuggestion(
                        "1",
                        "titulo muito legal e que deve aparecer no card",
                        "150,00",
                        "60",
                        "50,00",
                        "https://http2.mlstatic.cf-a3dc51f6e12f.png"
                    ),
                    MeLiSuggestion(
                        "2",
                        "titulo muito legal e que deve aparecer no card",
                        null,
                        null,
                        "50,00",
                        null
                    ),
                    MeLiSuggestion(
                        "3",
                        "titulo muito legal e que deve aparecer no card",
                        null,
                        null,
                        "50,00",
                        "https://http2.mlstatic.com/storage/categoriea3dc51f6e12f.png"
                    ),
                    MeLiSuggestion(
                        "4",
                        "titulo muito legal e que deve aparecer no card",
                        "150,00",
                        "60",
                        "50,00",
                        "https://http2.mlstatic.com/4266-a0cf-a3dc51f6e12f.png"
                    )
                )
    }
}
