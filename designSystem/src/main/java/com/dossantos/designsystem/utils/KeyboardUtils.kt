package com.dossantos.designsystem.utils

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import com.dossantos.designsystem.utils.Integers.zero

internal fun View.hideKeyboard() = context.getSystemService<InputMethodManager>()
    ?.hideSoftInputFromWindow(windowToken, zero)
