package com.dossantos.designsystem.toast

import android.content.Context
import android.widget.Toast

fun Context.meLiToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}