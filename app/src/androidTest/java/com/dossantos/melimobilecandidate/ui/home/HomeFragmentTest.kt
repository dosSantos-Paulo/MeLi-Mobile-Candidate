package com.dossantos.melimobilecandidate.ui.home

import androidx.core.view.isVisible
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dossantos.domain.usecase.category.CategoryMenuUseCase
import com.dossantos.domain.usecase.offer.OfferUseCase
import com.dossantos.domain.usecase.suggestions.SuggestionsUseCase
import com.dossantos.melimobilecandidate.R
import com.dossantos.melimobilecandidate.databinding.FragmentHomeBinding
import com.dossantos.melimobilecandidate.viewmodel.home.CategoryMenuUiState
import com.dossantos.melimobilecandidate.viewmodel.home.OfferUiState
import com.dossantos.melimobilecandidate.viewmodel.home.SuggestionsUiState
import io.mockk.coEvery
import io.mockk.mockkClass
import io.mockk.unmockkAll
import io.mockk.unmockkObject
import kotlinx.coroutines.flow.flow
import org.junit.After
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.koin.test.KoinTest
import org.koin.test.mock.MockProvider
import org.koin.test.mock.declareMock
import kotlin.test.fail

typealias JustIgnore = Unit

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class HomeFragmentTest : KoinTest {

    private lateinit var homeFragmentLauncher: FragmentScenario<HomeFragment>
    private lateinit var binding: FragmentHomeBinding

    @Before
    fun setup() {
        MockProvider.register { mockkClass(it) }
    }

    @After
    fun tearDown() {
        unmockkObject(OfferUiState::class)
        unmockkObject(CategoryMenuUseCase::class)
        unmockkObject(SuggestionsUseCase::class)
        unmockkAll()
    }

    @Test
    fun test_A_getOffersWithSuccess() {
        // Given
        setupHomeFragment()
        homeFragmentLauncher.onFragment { homeFragment ->

            // When
            homeFragment.viewModel.offerUiState.observe(homeFragment.viewLifecycleOwner) {

                // Then
                when (val state = it.uiState) {
                    is OfferUiState.StateUi.OnSuccess -> {
                        assert(binding.meLiOfferCarousel.isVisible)
                        assert(state.offers.isNotEmpty())
                    }

                    is OfferUiState.StateUi.OnError -> fail("State is OnError")

                    else -> JustIgnore
                }
            }
        }
    }

    @Test
    fun test_B_getCategoriesWithSuccess() {
        // Given
        setupHomeFragment()

        // When
        homeFragmentLauncher.onFragment { homeFragment ->

            // Then
            assert(binding.categoryLoading.isVisible)

            homeFragment.viewModel.categoryMenuUiState.observe(homeFragment.viewLifecycleOwner) {

                when (val state = it.uiState) {
                    is CategoryMenuUiState.StateUi.OnSuccess -> {
                        assert(binding.categoryLoading.isVisible.not())
                        assert(binding.categoryLayout.isVisible)
                        assert(state.categories.isNotEmpty())
                    }

                    is CategoryMenuUiState.StateUi.OnError -> fail("State is OnError")

                    else -> JustIgnore
                }
            }
        }
    }

    @Test
    fun test_C_getSuggestionsWithSuccess() {
        // Given
        setupHomeFragment()

        // When
        homeFragmentLauncher.onFragment { homeFragment ->

            // Then
            homeFragment.viewModel.suggestionsUiState.observe(homeFragment.viewLifecycleOwner) {

                when (val state = it.uiState) {
                    is SuggestionsUiState.StateUi.OnSuccess -> {
                        assert(binding.suggestionsRecyclerView.isVisible)
                        assert(state.suggestions.isNotEmpty())
                    }

                    is SuggestionsUiState.StateUi.OnError -> fail("State is OnError")

                    else -> JustIgnore
                }
            }
        }
    }

    @Test
    fun test_D_getOffersWithError() {
        // Given
        declareMock<OfferUseCase> {
            coEvery { getOffers() } returns flow { throw Exception("Exception") }
        }
        setupHomeFragment()

        // When
        homeFragmentLauncher.onFragment { homeFragment ->
            var retryCount = 0

            homeFragment.viewModel.offerUiState.observe(homeFragment.viewLifecycleOwner) {

                // Then
                when (it.uiState) {
                    is OfferUiState.StateUi.OnError -> {
                        retryCount++
                        assert(retryCount <= 3)
                    }

                    is OfferUiState.StateUi.OnSuccess -> fail("State is onSuccess")

                    else -> JustIgnore
                }
            }
        }
    }

    @Test
    fun test_E_getCategoriesWithError() {
        // Given
        declareMock<CategoryMenuUseCase> {
            coEvery { getMenuCategory() } returns flow { throw Exception("Exception") }
        }
        setupHomeFragment()

        // When
        homeFragmentLauncher.onFragment { homeFragment ->

            // Then
            var retryCount = 0
            assert(binding.categoryLoading.isVisible)

            homeFragment.viewModel.categoryMenuUiState.observe(homeFragment.viewLifecycleOwner) {

                when (it.uiState) {
                    is CategoryMenuUiState.StateUi.OnError -> {
                        retryCount++
                        assert(retryCount <= 3)
                        if (retryCount <= 3) {
                            assert(binding.categoryLayout.isVisible.not())
                        }
                    }

                    is CategoryMenuUiState.StateUi.OnSuccess -> fail("State is onSuccess")

                    else -> JustIgnore
                }
            }
        }
    }

    @Test
    fun test_F_getSuggestionsWithError() {
        // Given
        declareMock<SuggestionsUseCase> {
            coEvery { getSuggestions() } returns flow { throw Exception("Exception") }
        }
        setupHomeFragment()

        // When
        homeFragmentLauncher.onFragment { homeFragment ->

            // Then
            homeFragment.viewModel.suggestionsUiState.observe(homeFragment.viewLifecycleOwner) {
                var retryCount = 0

                when (it.uiState) {
                    is SuggestionsUiState.StateUi.OnError -> {
                        retryCount++
                        assert(retryCount <= 3)
                    }

                    is SuggestionsUiState.StateUi.OnSuccess -> fail("State is onSuccess")

                    else -> JustIgnore
                }
            }
        }
    }

    private fun setupHomeFragment() {
        homeFragmentLauncher = launchFragmentInContainer<HomeFragment>(
            initialState = Lifecycle.State.STARTED,
            themeResId = R.style.Theme_MeLiMobileCandidate
        )

        homeFragmentLauncher.onFragment { fragment ->
            fragment.view?.let { binding = FragmentHomeBinding.bind(it) }
        }
    }
}
