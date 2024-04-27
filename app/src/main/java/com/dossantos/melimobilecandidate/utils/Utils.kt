package com.dossantos.melimobilecandidate.utils

fun Any?.doIfNull(action: () -> Unit) = if (this == null) action() else ElseNothing
