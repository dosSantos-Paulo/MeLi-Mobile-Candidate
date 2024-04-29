package com.dossantos.melimobilecandidate.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dossantos.designsystem.ui.suggestions.MeLiSuggestionCard
import com.dossantos.melimobilecandidate.databinding.ItemHomeSuggestionsBinding

class HomeSuggestionsAdapter(
    val suggestions: List<Pair<String, List<MeLiSuggestionCard.Companion.MeLiSuggestion>>>
) : RecyclerView.Adapter<HomeSuggestionsAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemHomeSuggestionsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(suggestions: Pair<String, List<MeLiSuggestionCard.Companion.MeLiSuggestion>>) {
            binding.meLiSuggestion.setup(suggestions.first, suggestions.second)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemHomeSuggestionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = suggestions.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(suggestions[position])
    }
}
