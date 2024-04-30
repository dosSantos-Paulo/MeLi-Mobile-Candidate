package com.dossantos.designsystem.ui.suggestions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.dossantos.designsystem.databinding.MeliSuggestionItemBinding
import com.dossantos.designsystem.model.suggestions.MeLiSuggestion
import com.dossantos.designsystem.utils.checkAndUseImage

class MeLiSuggestionCardAdapter(
    private val suggestions: List<MeLiSuggestion>,
    private val onItemClicked: (suggestionId: String) -> Unit
) : RecyclerView.Adapter<MeLiSuggestionCardAdapter.ViewHolder>() {

    class ViewHolder(private val binding: MeliSuggestionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(
            suggestion: MeLiSuggestion,
            onItemClicked: (suggestionId: String) -> Unit
        ) {
            binding.image.checkAndUseImage(suggestion.imageUrl)

            suggestion.title?.let {
                binding.title.text = it
                binding.title.isVisible = true
            }

            suggestion.discountPercentage?.let {
                binding.offAmount.text = it
                binding.offAmount.isVisible = true
            }

            suggestion.price?.let {
                binding.price.text = it
                binding.price.isVisible = true
            }

            suggestion.lastPrice?.let {
                binding.lastPrice.text = it
                binding.lastPrice.isVisible = true
            }

            binding.card.setOnClickListener { suggestion.itemId?.let { it1 -> onItemClicked(it1) } }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        MeliSuggestionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = suggestions.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(suggestions[position], onItemClicked)
    }

}
