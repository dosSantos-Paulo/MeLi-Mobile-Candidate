package com.dossantos.melimobilecandidate.ui.home

import android.os.Bundle
import android.view.View
import com.dossantos.designsystem.R
import com.dossantos.designsystem.offer.MeLiOfferAdapter
import com.dossantos.designsystem.offer.MeLiOfferCardFragment
import com.dossantos.melimobilecandidate.databinding.FragmentHomeBinding
import com.dossantos.melimobilecandidate.ui.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.meLiOfferCarousel.setupAdapter(
            MeLiOfferAdapter(
                listOf(
                    MeLiOfferCardFragment.Companion.MeLiOffer(R.drawable.auto, "auto"),
                    MeLiOfferCardFragment.Companion.MeLiOffer(R.drawable.cama_mesa_e_banho, "cama"),
                    MeLiOfferCardFragment.Companion.MeLiOffer(R.drawable.suplemento, "suplemento"),
                    MeLiOfferCardFragment.Companion.MeLiOffer(R.drawable.primeira_compra, "primeira"),
                    MeLiOfferCardFragment.Companion.MeLiOffer(R.drawable.cama_mesa_e_banho_2, "cama"),
                ),
                requireActivity()
            )
        )
    }
}
