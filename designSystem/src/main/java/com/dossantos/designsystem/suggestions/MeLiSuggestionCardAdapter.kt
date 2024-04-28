package com.dossantos.designsystem.suggestions

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.dossantos.designsystem.R
import com.dossantos.designsystem.databinding.MeliSuggestionItemBinding
import com.dossantos.designsystem.utils.checkAndUseImage

class MeLiSuggestionCardAdapter(
    val suggestions: List<MeLiSuggestionCard.Companion.MeLiSuggestion>,
    val onItemClicked: (suggestionId: String) -> Unit
) : RecyclerView.Adapter<MeLiSuggestionCardAdapter.ViewHolder>() {

    class ViewHolder(val binding: MeliSuggestionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(
            suggestion: MeLiSuggestionCard.Companion.MeLiSuggestion,
            onItemClicked: (suggestionId: String) -> Unit
        ) {
            binding.image.checkAndUseImage(suggestion.imageUrl)
            val context = binding.root.context

            suggestion.title?.let {
                binding.title.text = it
                binding.title.isVisible = true
            }

            suggestion.discountPercentage?.let {
                binding.offAmount.text = context.getString(R.string.discount, it)
                binding.offAmount.isVisible = true
            }

            suggestion.price?.let {
                binding.price.text = context.getString(R.string.price, it)
                binding.price.isVisible = true
            }

            suggestion.lastPrice?.let {
                binding.lastPrice.text = context.getString(R.string.lastPrice, it)
                binding.lastPrice.isVisible = true
            }

            binding.card.setOnClickListener { onItemClicked(suggestion.itemId) }
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
