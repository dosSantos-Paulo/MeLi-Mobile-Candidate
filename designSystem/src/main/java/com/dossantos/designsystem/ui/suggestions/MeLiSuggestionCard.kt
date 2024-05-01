package com.dossantos.designsystem.ui.suggestions

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.dossantos.designsystem.databinding.MeliSuggestionsCardBinding
import com.dossantos.designsystem.model.suggestions.MeLiProducts

class MeLiSuggestionCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: MeliSuggestionsCardBinding by lazy {
        MeliSuggestionsCardBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private var adapter: MeLiSuggestionCardAdapter? = null

    fun setup(title: String, suggestions: List<MeLiProducts>?, onClick: (suggestionId: String) -> Unit) {
        if (suggestions == null) return

        binding.suggestionTitle.text = title
        adapter = MeLiSuggestionCardAdapter(suggestions, onClick)
        binding.recyclerView.adapter = adapter
    }
}
