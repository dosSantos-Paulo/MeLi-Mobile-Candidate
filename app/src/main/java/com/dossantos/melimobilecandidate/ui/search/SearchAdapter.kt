package com.dossantos.melimobilecandidate.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.dossantos.designsystem.databinding.MeliSuggestionItemBinding
import com.dossantos.designsystem.model.suggestions.MeLiProducts
import com.dossantos.melimobilecandidate.utils.checkAndUseImage

class SearchAdapter(
    private val products: MutableList<MeLiProducts>,
    private val onProductClickListener: (productId: String) -> Unit
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    class ViewHolder(val binding: MeliSuggestionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(product: MeLiProducts, onProductClickListener: (productId: String) -> Unit) {
            binding.image.checkAndUseImage(product.imageUrl)

            product.title?.let {
                binding.title.text = it
                binding.title.isVisible = true
            }

            product.discountPercentage?.let {
                binding.offAmount.text = it
                binding.offAmount.isVisible = true
            }

            product.price?.let {
                binding.price.text = it
                binding.price.isVisible = true
            }

            product.lastPrice?.let {
                binding.lastPrice.text = it
                binding.lastPrice.isVisible = true
            }

            binding.card.setOnClickListener {
                product.itemId?.let { it1 -> onProductClickListener(it1) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        MeliSuggestionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(products[position], onProductClickListener)
    }

    fun updateList(newProducts: List<MeLiProducts>) {
        val startPosition = products.size
        products.addAll(newProducts)
        notifyItemRangeInserted(startPosition, newProducts.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        products.clear()
        notifyDataSetChanged()
    }
}
