package com.dossantos.designsystem.offer

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager.PageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.dossantos.designsystem.databinding.MeliOfferCarouselBinding
import com.dossantos.designsystem.offer.MeLiOfferCardFragment.Companion.MeLiOffer
import com.dossantos.designsystem.utils.Integers.one
import com.dossantos.designsystem.utils.Long.fourSeconds

class MeLiOfferCarousel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: MeliOfferCarouselBinding by lazy {
        MeliOfferCarouselBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private var adapter: MeLiOfferAdapter? = null

    init {
        if (isInEditMode) setup(
            listOf(MeLiOffer("", "", "")),
            context as FragmentActivity
        )
    }

    fun setup(offers: List<MeLiOffer>, activity: FragmentActivity) {
        this.adapter = MeLiOfferAdapter(offers, activity)
        binding.viewPager.adapter = adapter
        autoSwipe()
    }

    fun setOnCardClickListener(onClick: (String) -> Unit) {
        adapter?.setOnCardClickedListener(onClick)
    }

    private fun autoSwipe() {
        Handler(Looper.getMainLooper()).postDelayed({
            binding.viewPager.setCurrentItem(binding.viewPager.currentItem + one, true)
            autoSwipe()
        }, fourSeconds)
    }
}
