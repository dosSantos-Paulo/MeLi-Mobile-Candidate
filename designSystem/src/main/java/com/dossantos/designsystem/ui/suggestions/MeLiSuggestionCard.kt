package com.dossantos.designsystem.ui.suggestions

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.dossantos.designsystem.databinding.MeliSuggestionsCardBinding
import com.dossantos.designsystem.model.suggestions.MeLiSuggestion

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
}
