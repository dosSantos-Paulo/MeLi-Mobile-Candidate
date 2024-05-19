package com.dossantos.melimobilecandidate.ui.home

import androidx.core.view.isVisible
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dossantos.melimobilecandidate.R
import com.dossantos.melimobilecandidate.databinding.FragmentHomeBinding
import com.dossantos.melimobilecandidate.viewmodel.home.OfferUiState
import io.mockk.mockkClass
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.mock.MockProvider
import kotlin.test.fail

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest : KoinTest {

    private lateinit var homeFragmentLauncher: FragmentScenario<HomeFragment>

    @Before
    fun setup() {
        MockProvider.register { mockkClass(it) }

        homeFragmentLauncher = launchFragmentInContainer<HomeFragment>(
            initialState = Lifecycle.State.STARTED,
            themeResId = R.style.Theme_MeLiMobileCandidate
        )
    }

    @Test
    fun testIfHomeFragmentIsLaunched() {
        // Given
        var binding: FragmentHomeBinding? = null

        // When
        homeFragmentLauncher.onFragment { homeFragment ->
            homeFragment.view?.let { binding = FragmentHomeBinding.bind(it) }
        }

        // Then
        assert(binding != null)
    }

    @Test
    fun testGetOffersWithSuccess() {
        // Given
        var binding: FragmentHomeBinding? = null

        // When
        homeFragmentLauncher.onFragment { homeFragment ->

            homeFragment.view?.let { binding = FragmentHomeBinding.bind(it) }

            homeFragment.viewModel.offerUiState.observe(homeFragment.viewLifecycleOwner) {
                if (binding == null) fail("Binding is null")

                // Then
                when (val state = it.uiState) {
                    is OfferUiState.StateUi.OnSuccess -> {
                        assert(binding!!.meLiOfferCarousel.isVisible)
                        assert(state.offers.isNotEmpty())
                    }

                    else -> fail("State is not OnSuccess")
                }
            }
        }
    }
}
