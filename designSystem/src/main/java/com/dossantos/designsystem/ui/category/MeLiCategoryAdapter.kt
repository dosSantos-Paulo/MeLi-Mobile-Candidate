package com.dossantos.designsystem.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dossantos.designsystem.databinding.MeliCategoryBinding
import com.dossantos.designsystem.model.category.MeLiCategory

class MeLiCategoryAdapter(
    private val categoryList: List<MeLiCategory>,
    private val onCategoryClickListener: (String) -> Unit
) : RecyclerView.Adapter<MeLiCategoryAdapter.ViewHolder>() {
    class ViewHolder(val binding: MeliCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            category: MeLiCategory,
            onCategoryClickListener: (String) -> Unit
        ) {
            binding.categoryTitle.text = category.name
            Glide.with(binding.root).load(category.imageUrl).into(binding.image)
            binding.image.contentDescription = category.name

            binding.card.setOnClickListener {
                onCategoryClickListener(category.id.orEmpty())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        MeliCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = categoryList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(categoryList[position], onCategoryClickListener)
    }
}
