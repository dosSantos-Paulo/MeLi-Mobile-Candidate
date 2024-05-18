package com.dossantos.melimobilecandidate.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.dossantos.melimobilecandidate.R
import com.dossantos.melimobilecandidate.di.getMainModules
import com.dossantos.melimobilecandidate.viewmodel.home.CategoryMenuUiState
import com.dossantos.melimobilecandidate.viewmodel.home.HomeViewModel
import com.dossantos.melimobilecandidate.viewmodel.home.OfferUiState
import com.dossantos.melimobilecandidate.viewmodel.home.SuggestionsUiState
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkClass
import io.mockk.slot
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.mock.MockProvider
import org.koin.test.mock.declareMock

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest: KoinTest {

//    @get:Rule
//    val koinTestRule = MockProvider.register {
//        mockkClass(it)
//    }

//    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()

//    private val app = koinApplication {
//        androidLogger()
//        androidContext(getInstrumentation().targetContext.applicationContext)
//        getMainModules()
//    }

//    val offerObserver: Observer<in OfferUiState> = mockk(relaxed = true)
//    val categoryObserver: Observer<in CategoryMenuUiState> = mockk(relaxed = true)
//    val suggestionsObserver: Observer<in SuggestionsUiState> = mockk(relaxed = true)
//
//    val slotOffer = slot<OfferUiState>()
//    val slotCategory = slot<CategoryMenuUiState>()
//    val slotSuggestions = slot<SuggestionsUiState>()
//
//    @Before
//    fun setup() {
//        with(app.koin) {
//            declareMock<HomeViewModel> {
//                every { init() } just Runs
//            }
//        }
//    }


    @Test
    fun testIfHomeFragmentIsLaunched() {
        // Given
        val homeFragmentLauncher = launchFragmentInContainer<HomeFragment>(
            initialState = Lifecycle.State.STARTED,
            themeResId = R.style.Theme_MeLiMobileCandidate
        )

        // When
        homeFragmentLauncher.onFragment { homeFragment ->

            // Then
            assert(homeFragment.view != null)
        }
    }

//    @Test
//    fun testGetOffers() {
//        // Given
////        with(app.koin)
//
//        val homeFragmentLauncher = launchFragmentInContainer<HomeFragment>(
//            initialState = Lifecycle.State.STARTED,
//            themeResId = R.style.Theme_MeLiMobileCandidate
//        )
//
//        // When
////        homeFragmentLauncher.onFragment { homeFragment ->
////            homeFragment.viewModel = mockViewModel
////        }
//    }
}
