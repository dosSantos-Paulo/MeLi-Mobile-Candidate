package com.dossantos.melimobilecandidate.ui.home

import android.os.Bundle
import android.view.View
import com.dossantos.designsystem.R
import com.dossantos.designsystem.category.MeLiCategoryCarousel
import com.dossantos.designsystem.offer.MeLiOfferAdapter
import com.dossantos.designsystem.offer.MeLiOfferCardFragment
import com.dossantos.melimobilecandidate.databinding.FragmentHomeBinding
import com.dossantos.melimobilecandidate.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel by activityViewModel<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
        binding.meLiOfferCarousel.setup(
            MeLiOfferAdapter(
                listOf(
                    MeLiOfferCardFragment.Companion.MeLiOffer(R.drawable.offer_auto, "auto"),
                    MeLiOfferCardFragment.Companion.MeLiOffer(R.drawable.offer_bed_1, "cama"),
                    MeLiOfferCardFragment.Companion.MeLiOffer(R.drawable.offer_first_shop, "suplemento"),
                    MeLiOfferCardFragment.Companion.MeLiOffer(R.drawable.offer_bed_2, "cama"),
                    MeLiOfferCardFragment.Companion.MeLiOffer(R.drawable.offer_sup, "cama"),
                ),
                requireActivity()
            )
        )
        binding.meLiCategoryCarousel.setup(
            listOf(
                MeLiCategoryCarousel.Companion.MeLiCategory("auto", "https://http2.mlstatic.com/storage/categories-api/images/985c3a8d-ea5b-4266-a0cf-a3dc51f6e12f.png"),
                MeLiCategoryCarousel.Companion.MeLiCategory("auto", "https://http2.mlstatic.com/storage/categories-api/images/985c3a8d-ea5b-4266-a0cf-a3dc51f6e12f.png"),
                MeLiCategoryCarousel.Companion.MeLiCategory("auto", "https://http2.mlstatic.com/storage/categories-api/images/985c3a8d-ea5b-4266-a0cf-a3dc51f6e12f.png"),
                MeLiCategoryCarousel.Companion.MeLiCategory("auto", "https://http2.mlstatic.com/storage/categories-api/images/985c3a8d-ea5b-4266-a0cf-a3dc51f6e12f.png"),
                MeLiCategoryCarousel.Companion.MeLiCategory("auto", "https://http2.mlstatic.com/storage/categories-api/images/985c3a8d-ea5b-4266-a0cf-a3dc51f6e12f.png"),
                MeLiCategoryCarousel.Companion.MeLiCategory("auto", "https://http2.mlstatic.com/storage/categories-api/images/985c3a8d-ea5b-4266-a0cf-a3dc51f6e12f.png"),
                MeLiCategoryCarousel.Companion.MeLiCategory("auto", "https://http2.mlstatic.com/storage/categories-api/images/985c3a8d-ea5b-4266-a0cf-a3dc51f6e12f.png"),
                MeLiCategoryCarousel.Companion.MeLiCategory("auto", "https://http2.mlstatic.com/storage/categories-api/images/985c3a8d-ea5b-4266-a0cf-a3dc51f6e12f.png"),
                MeLiCategoryCarousel.Companion.MeLiCategory("auto", "https://http2.mlstatic.com/storage/categories-api/images/985c3a8d-ea5b-4266-a0cf-a3dc51f6e12f.png"),
                MeLiCategoryCarousel.Companion.MeLiCategory("auto", "https://http2.mlstatic.com/storage/categories-api/images/985c3a8d-ea5b-4266-a0cf-a3dc51f6e12f.png"),
                MeLiCategoryCarousel.Companion.MeLiCategory("auto", "https://http2.mlstatic.com/storage/categories-api/images/985c3a8d-ea5b-4266-a0cf-a3dc51f6e12f.png"),
            )
        )
    }
}
