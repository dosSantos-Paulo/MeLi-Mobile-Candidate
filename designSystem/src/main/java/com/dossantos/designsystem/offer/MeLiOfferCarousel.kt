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
            setupAdapter(
                MeLiOfferAdapter(
                    listOf(MeLiOfferCardFragment.Companion.MeLiOffer(R.drawable.auto, "auto")),
                    this.parent as FragmentActivity
                )
            )
        }
    }

    fun setupAdapter(adapter: MeLiOfferAdapter) {
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