package com.dossantos.designsystem.ui.imagecarousel

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.viewpager2.widget.ViewPager2
import com.dossantos.designsystem.R
import com.dossantos.designsystem.databinding.MeliImageCarouselBinding
import com.dossantos.designsystem.utils.Integers
import com.dossantos.designsystem.utils.Integers.one
import com.dossantos.designsystem.utils.Integers.zero
import com.dossantos.designsystem.utils.Long

class MeLiImageCarousel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: MeliImageCarouselBinding by lazy {
        MeliImageCarouselBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private var total = zero

    fun setup(images: List<String>, fragmentActivity: FragmentActivity) {
        total = images.size
        binding.viewPager.adapter = MeLiImageCarouselAdapter(images, fragmentActivity)
        setupBadges()
    }

    private fun setupBadges() {
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                binding.badgeCount.text =
                    context.getString(
                        R.string.badge_count,
                        (binding.viewPager.currentItem + one).toString(),
                        total.toString()
                    )
            }

            override fun onPageSelected(position: Int) = Unit

            override fun onPageScrollStateChanged(state: Int) = Unit

        })

    }
}