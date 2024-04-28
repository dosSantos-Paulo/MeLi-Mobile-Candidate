package com.dossantos.designsystem.suggestions

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.dossantos.designsystem.databinding.MeliSuggestionsCardBinding

class MeLiSuggestionCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: MeliSuggestionsCardBinding by lazy {
        MeliSuggestionsCardBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private var adapter: MeLiSuggestionCardAdapter? = null

    private var onItemClicked: (suggestionId: String) -> Unit = {}

    init {
        if (isInEditMode) {
            setup("", listOf(MeLiSuggestion("", "", "2500,00", "","2500,00", null)))
        }
    }

    fun setup(title: String, suggestions: List<MeLiSuggestion>) {
        binding.suggestionTitle.text = title
        adapter = MeLiSuggestionCardAdapter(suggestions, onItemClicked)
        binding.recyclerView.adapter = adapter
    }

    fun setOnItemClickListener(onClick: (suggestionId: String) -> Unit) {
        onItemClicked = onClick
    }

    companion object {
        data class MeLiSuggestion(
            val itemId: String,
            val title: String?,
            val lastPrice: String?,
            val discountPercentage: String?,
            val price: String?,
            val imageUrl: String?
        )
    }
}
