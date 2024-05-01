package com.dossantos.melimobilecandidate.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.dossantos.melimobilecandidate.R

fun Any?.doIfNull(action: () -> Unit) = if (this == null) action() else ElseNothing

fun String?.orUnknownError(context: Context) =
    if (this.isNullOrEmpty()) context.getString(R.string.unknown_error) else this

fun ImageView.checkAndUseImage(imageUrl: String?) {
    Glide
        .with(this)
        .load(imageUrl ?: "https://triunfo.pe.gov.br/pm_tr430/wp-content/uploads/2018/03/sem-foto.jpg")
        .into(this)
}

fun ImageView.checkAndUseImage(imageUrl: Int?) {
    Glide
        .with(this)
        .load(imageUrl ?: "https://triunfo.pe.gov.br/pm_tr430/wp-content/uploads/2018/03/sem-foto.jpg")
        .into(this)
}
