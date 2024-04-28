package com.dossantos.designsystem.offer

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.dossantos.designsystem.databinding.MeliOfferBinding
import kotlinx.parcelize.Parcelize

class MeLiOfferCardFragment(val onImageClickListener: (category: String) -> Unit) : Fragment() {

    private lateinit var binding: MeliOfferBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = MeliOfferBinding
        .inflate(inflater, container, false)
        .also { binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupImage()
        setupOnClickListener()
    }

    private fun setupImage() {
        Glide.with(binding.root).load(getArgument()?.image).into(binding.image)
    }

    private fun setupOnClickListener() {
        binding.image.setOnClickListener {
            getArgument()?.let { onImageClickListener(it.category) }
        }
    }

    private fun getArgument() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) getOnNewApi()
        else getOnOldApi()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getOnNewApi() = arguments?.getParcelable(EXTRA_OFFER, MeLiOffer::class.java)

    @Suppress("DEPRECATION")
    private fun getOnOldApi() = arguments?.get(EXTRA_OFFER) as? MeLiOffer

    companion object {
        const val EXTRA_OFFER = "EXTRA_OFFER"

        @Parcelize
        data class MeLiOffer(@DrawableRes val image: Int, val category: String) : Parcelable
    }
}
