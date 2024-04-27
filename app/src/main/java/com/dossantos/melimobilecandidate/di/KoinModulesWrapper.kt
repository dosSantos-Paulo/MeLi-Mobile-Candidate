package com.dossantos.melimobilecandidate.di

import com.dossantos.melimobilecandidate.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun getKoinModules() = koinModules

private val koinModules by lazy {
    loadKoinModules(listOf(viewModelModule))
}

private val viewModelModule = module {
    viewModel { HomeViewModel() }
}
