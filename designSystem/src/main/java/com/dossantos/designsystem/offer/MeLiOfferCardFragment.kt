package com.dossantos.designsystem.offer

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.dossantos.designsystem.R
import com.dossantos.designsystem.databinding.MeliOfferBinding
import com.dossantos.designsystem.utils.checkAndUseImage
import kotlinx.parcelize.Parcelize

class MeLiOfferCardFragment : Fragment() {

    private lateinit var binding: MeliOfferBinding

    private var onOfferClickListener: (String) -> Unit = {}

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
        setupOnClickListeners()
    }

    fun setOnOfferClickListener(onAction: (offerId: String) -> Unit) {
        onOfferClickListener = onAction
    }

    private fun setupOnClickListeners() {
        binding.image.setOnClickListener {
            getArgument()?.id?.let { id -> onOfferClickListener(id) }
        }
    }

    private fun setupImage() {
        binding.image.checkAndUseImage(getArgument()?.id?.getPng())
    }

    private fun getArgument() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) getOnNewApi()
        else getOnOldApi()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getOnNewApi() = arguments?.getParcelable(EXTRA_OFFER, MeLiOffer::class.java)

    @Suppress("DEPRECATION")
    private fun getOnOldApi() = arguments?.get(EXTRA_OFFER) as? MeLiOffer

    companion object {

        /**
         * Método criado para solucionar falta de acesso aos links do MeLi
         */
        private fun String.getPng() = when (this) {
            "MLB1051" -> R.drawable.mlb1051
            "MLB1276" -> R.drawable.mlb1276
            "MLB1574" -> R.drawable.mlb1574
            "MLB5726" -> R.drawable.mlb5726
            else -> R.drawable.mlb1000
        }

        const val EXTRA_OFFER = "EXTRA_OFFER"

        @Parcelize
        data class MeLiOffer(
            val imageUrl: String?,
            val id: String?,
            val contentDescription: String?
        ) : Parcelable
    }
}
