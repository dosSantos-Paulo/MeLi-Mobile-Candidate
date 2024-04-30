package com.dossantos.designsystem.ui.offer

import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dossantos.designsystem.model.offer.MeLiOffer
import com.dossantos.designsystem.ui.toast.meLiToast

class MeLiOfferAdapter(
    val offerList: List<MeLiOffer>,
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    private var onCardClicked: (category: String) -> Unit = { category ->
        fragmentActivity.meLiToast(category)
    }

    override fun getItemCount() = offerList.size

    override fun createFragment(position: Int) = MeLiOfferCardFragment().apply {
        setOnOfferClickListener(onCardClicked)
        arguments = bundleOf(MeLiOfferCardFragment.EXTRA_OFFER to offerList[position])
    }

    fun setOnCardClickedListener(onClick: (category: String) -> Unit) {
        onCardClicked = onClick
    }
}
