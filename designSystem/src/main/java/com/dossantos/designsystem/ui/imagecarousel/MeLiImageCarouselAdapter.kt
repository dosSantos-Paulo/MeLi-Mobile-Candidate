package com.dossantos.designsystem.ui.imagecarousel

import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MeLiImageCarouselAdapter(
    private val imagesUrl: List<String>,
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = imagesUrl.size

    override fun createFragment(position: Int) = MeLiImageFragment()
        .apply { arguments = bundleOf(MeLiImageFragment.EXTRA_IMAGE to imagesUrl[position]) }
}
