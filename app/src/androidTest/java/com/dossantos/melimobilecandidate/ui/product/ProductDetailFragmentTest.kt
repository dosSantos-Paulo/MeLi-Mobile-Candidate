package com.dossantos.melimobilecandidate.ui.product

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductDetailFragmentTest {

    @Test
    fun testOnViewCreated() {

        val productionDetailFragment = launchFragmentInContainer<ProductDetailFragment>(
            initialState = Lifecycle.State.STARTED
        )

//        productionDetailFragment.moveToState(Lifecycle.State.RESUMED)

//        onView(withId())
    }
}