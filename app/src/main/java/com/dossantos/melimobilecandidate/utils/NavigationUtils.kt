package com.dossantos.melimobilecandidate.utils

import android.os.Bundle
import androidx.navigation.NavController
import com.dossantos.melimobilecandidate.R

fun NavController.searchToSelf(bundle: Bundle? = null) {
    navigate(R.id.action_searchFragment_self, bundle)
}

fun NavController.homeToSearch(bundle: Bundle? = null) {
    navigate(R.id.action_homeFragment_to_searchFragment, bundle)
}

fun NavController.homeToDetail(bundle: Bundle? = null) {
    navigate(R.id.action_homeFragment_to_productDetailFragment, bundle)
}

fun NavController.searchToDetail(bundle: Bundle? = null) {
    navigate(R.id.action_searchFragment_to_productDetailFragment, bundle)

}