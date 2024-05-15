package com.dossantos.melimobilecandidate.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dossantos.designsystem.model.suggestions.MeLiProducts
import com.dossantos.melimobilecandidate.databinding.ItemHomeSuggestionsBinding

class HomeSuggestionsAdapter(
    private val suggestions: List<Pair<String?, List<MeLiProducts>?>>,
    private val onCardClickListener: (String) -> Unit
) : RecyclerView.Adapter<HomeSuggestionsAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemHomeSuggestionsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            suggestions: Pair<String?, List<MeLiProducts>?>,
            onCardClickListener: (String) -> Unit
        ) {
            binding.meLiSuggestion.setup(suggestions.first.orEmpty(), suggestions.second, onCardClickListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemHomeSuggestionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = suggestions.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(suggestions[position], onCardClickListener)
    }
}
