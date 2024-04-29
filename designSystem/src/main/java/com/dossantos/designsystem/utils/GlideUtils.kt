package com.dossantos.designsystem.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

internal fun ImageView.checkAndUseImage(imageUrl: String?) {
    Glide
        .with(this)
        .load(imageUrl ?: "https://triunfo.pe.gov.br/pm_tr430/wp-content/uploads/2018/03/sem-foto.jpg")
        .into(this)
}

internal fun ImageView.checkAndUseImage(imageUrl: Int?) {
    Glide
        .with(this)
        .load(imageUrl ?: "https://triunfo.pe.gov.br/pm_tr430/wp-content/uploads/2018/03/sem-foto.jpg")
        .into(this)
}
