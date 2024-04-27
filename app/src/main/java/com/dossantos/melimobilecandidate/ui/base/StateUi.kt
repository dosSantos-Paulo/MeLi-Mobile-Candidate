package com.dossantos.melimobilecandidate.ui.base

abstract class StateUi {
    abstract fun onError(message: String): StateUi
    abstract fun showLoading(): StateUi
}
