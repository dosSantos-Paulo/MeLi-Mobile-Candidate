package com.dossantos.designsystem.ui.imagecarousel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dossantos.designsystem.databinding.MeliImageBinding
import com.dossantos.designsystem.utils.checkAndUseImage

class MeLiImageFragment: Fragment() {

    private lateinit var binding: MeliImageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = MeliImageBinding
        .inflate(inflater, container, false)
        .also { binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.image.checkAndUseImage(arguments?.getString(EXTRA_IMAGE, null))
    }

    companion object {
        const val EXTRA_IMAGE = "EXTRA_IMAGE"
    }
}