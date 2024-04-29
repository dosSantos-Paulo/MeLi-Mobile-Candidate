package com.dossantos.designsystem.category

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.dossantos.designsystem.databinding.MeliCategoryCarouselBinding

class MeLiCategoryCarousel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: MeliCategoryCarouselBinding by lazy {
        MeliCategoryCarouselBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private var adapter: MeLiCategoryAdapter? = null

    private var setOnCategoryClickListener: (MeLiCategory) -> Unit = {}

    init {
        if (isInEditMode) setup(listOf(MeLiCategory("auto", "", "")))
    }

    fun setup(category: List<MeLiCategory>) {
        this.adapter = MeLiCategoryAdapter(category, setOnCategoryClickListener)
        binding.recyclerView.adapter = adapter
    }

    fun setOnCategoryClickListener(onClick: (MeLiCategory) -> Unit) {
        setOnCategoryClickListener = onClick
    }

    companion object {
        data class MeLiCategory(
            val name: String?,
            val imageUrl: String?,
            val id: String?
        )
    }
}
