package com.dossantos.melimobilecandidate.ui.search

import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dossantos.domain.usecase.search.SearchUseCase
import com.dossantos.melimobilecandidate.R
import com.dossantos.melimobilecandidate.databinding.FragmentSearchBinding
import com.dossantos.melimobilecandidate.ui.JustIgnore
import com.dossantos.melimobilecandidate.ui.product.ProductDetailFragment
import com.dossantos.melimobilecandidate.viewmodel.search.SearchStateUi
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

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SearchFragmentTest : KoinTest {

    private lateinit var searchFragmentLauncher: FragmentScenario<SearchFragment>
    private lateinit var binding: FragmentSearchBinding
    private val mockedCategoryId = "MLB3721292216"

    @Before
    fun setup() {
        MockProvider.register { mockkClass(it) }
    }

    @After
    fun tearDown() {
        unmockkObject(SearchUseCase::class)
        unmockkAll()
    }

    @Test
    fun test_A_getSearchResultWithSuccess() {
        // Given
        setupSearchFragment()

        // When
        searchFragmentLauncher.onFragment { fragment ->
            fragment.viewModel.searchStateUi.observe(fragment.viewLifecycleOwner) {

                // Then
                when (val state = it.uiState) {

                    is SearchStateUi.StateUi.OnSuccess -> {
                        assert(state.products.isNotEmpty())
                        assert(binding.loadingLayout.root.isVisible.not())
                    }

                    is SearchStateUi.StateUi.ShowLoading ->
                        assert(binding.loadingLayout.root.isVisible)

                    is SearchStateUi.StateUi.OnError -> fail("SearchResult is onError")

                    else -> JustIgnore
                }
            }
        }
    }

    @Test
    fun test_B_getSearchResultWithError() {
        // Given
        declareMock<SearchUseCase> {
            coEvery {
                searchProductByString(
                    any<String>(),
                    any<Int>(),
                )
            } returns flow { throw Exception("mocked error") }
        }

        setupSearchFragment()

        // When
        searchFragmentLauncher.onFragment { fragment ->
            fragment.viewModel.searchStateUi.observe(fragment.viewLifecycleOwner) {

                // Then
                when (it.uiState) {

                    is SearchStateUi.StateUi.OnError -> {
                        assert(binding.errorLayout.root.isVisible)
                    }

                    is SearchStateUi.StateUi.OnSuccess -> fail("SearchResult is onSuccess")

                    else -> JustIgnore
                }
            }
        }
    }

    private fun setupSearchFragment() {
        searchFragmentLauncher = launchFragmentInContainer<SearchFragment>(
            fragmentArgs = bundleOf(ProductDetailFragment.SEARCH_PRODUCT to mockedCategoryId),
            initialState = Lifecycle.State.STARTED,
            themeResId = R.style.Theme_MeLiMobileCandidate
        )

        searchFragmentLauncher.onFragment { fragment ->
            fragment.view?.let { binding = FragmentSearchBinding.bind(it) }
        }
    }
}