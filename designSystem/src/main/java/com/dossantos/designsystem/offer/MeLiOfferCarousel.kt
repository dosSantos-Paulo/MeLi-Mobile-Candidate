package com.dossantos.designsystem.offer

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import com.dossantos.designsystem.R
import com.dossantos.designsystem.databinding.MeliOfferCarouselBinding
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
        if (isInEditMode) {
            setup(
                MeLiOfferAdapter(
                    listOf(MeLiOfferCardFragment.Companion.MeLiOffer(R.drawable.offer_auto, "auto")),
                    this.context as FragmentActivity
                )
            )
        }
    }

    fun setup(adapter: MeLiOfferAdapter) {
        this.adapter = adapter
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