package com.dossantos.designsystem.ui.toast

import android.content.Context
import android.widget.Toast

fun Context.meLiToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
