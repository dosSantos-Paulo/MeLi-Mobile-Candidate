package com.dossantos.designsystem.model.offer

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MeLiOffer(
    val imageUrl: String?,
    val id: String?,
    val contentDescription: String?
) : Parcelable
