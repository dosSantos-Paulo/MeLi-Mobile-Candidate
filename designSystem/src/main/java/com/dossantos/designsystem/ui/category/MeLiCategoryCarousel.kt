package com.dossantos.designsystem.ui.category

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.dossantos.designsystem.databinding.MeliCategoryCarouselBinding
import com.dossantos.designsystem.model.category.MeLiCategory

class MeLiCategoryCarousel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: MeliCategoryCarouselBinding by lazy {
        MeliCategoryCarouselBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private var adapter: MeLiCategoryAdapter? = null

    init {
        if (isInEditMode) setup(listOf(MeLiCategory("auto", "", "")), {})
    }

    fun setup(category: List<MeLiCategory>, onClick: (String) -> Unit) {
        this.adapter = MeLiCategoryAdapter(category, onClick)
        binding.recyclerView.adapter = adapter
    }
}
