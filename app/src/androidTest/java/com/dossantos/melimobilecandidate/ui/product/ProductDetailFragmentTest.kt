package com.dossantos.melimobilecandidate.ui.product

import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dossantos.domain.usecase.products.ProductsUseCase
import com.dossantos.melimobilecandidate.R
import com.dossantos.melimobilecandidate.databinding.FragmentProductDetailBinding
import com.dossantos.melimobilecandidate.ui.JustIgnore
import com.dossantos.melimobilecandidate.viewmodel.product.ProductDetailStateUi
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
class ProductDetailFragmentTest : KoinTest {

    private lateinit var productDetailFragmentLauncher: FragmentScenario<ProductDetailFragment>
    private lateinit var binding: FragmentProductDetailBinding
    private val mockedProductId = "MLB4161118144"

    @Before
    fun setup() {
        MockProvider.register { mockkClass(it) }
    }

    @After
    fun tearDown() {
        unmockkObject(ProductsUseCase::class)
        unmockkAll()
    }

    @Test
    fun test_A_getProductWithSuccess() {
        // Given
        setupDetailFragment()

        // When
        productDetailFragmentLauncher.onFragment { fragment ->

            // Then
            fragment.viewModel.productDetailStateUi.observe(fragment.viewLifecycleOwner) {
                when (val state = it.uiState) {
                    is ProductDetailStateUi.StateUi.OnSuccess -> {
                        assert(binding.loadingView.root.isVisible.not())
                        assert(state.product.id == mockedProductId)
                    }

                    is ProductDetailStateUi.StateUi.ShowLoading ->
                        assert(binding.loadingView.root.isVisible)

                    is ProductDetailStateUi.StateUi.OnError ->
                        fail("ProductDetail error")

                    else -> JustIgnore
                }
            }
        }
    }

    @Test
    fun test_B_getProductWithSuccess() {
        // Given
        declareMock<ProductsUseCase> {
            coEvery { getProductById(mockedProductId) } returns flow { throw Exception("Mocked Error") }
        }
        setupDetailFragment()

        // When
        productDetailFragmentLauncher.onFragment { fragment ->

            // Then
            fragment.viewModel.productDetailStateUi.observe(fragment.viewLifecycleOwner) {
                when (it.uiState) {
                    is ProductDetailStateUi.StateUi.OnError ->
                        assert(binding.errorView.root.isVisible)

                    is ProductDetailStateUi.StateUi.OnSuccess ->
                        fail("ProductDetail error")

                    is ProductDetailStateUi.StateUi.ShowLoading ->
                        assert(binding.loadingView.root.isVisible)

                    else -> JustIgnore
                }
            }
        }
    }


    private fun setupDetailFragment() {
        productDetailFragmentLauncher = launchFragmentInContainer<ProductDetailFragment>(
            fragmentArgs = bundleOf(ProductDetailFragment.SEARCH_PRODUCT to mockedProductId),
            initialState = Lifecycle.State.STARTED,
            themeResId = R.style.Theme_MeLiMobileCandidate
        )

        productDetailFragmentLauncher.onFragment { fragment ->
            fragment.view?.let { binding = FragmentProductDetailBinding.bind(it) }
        }
    }

}
