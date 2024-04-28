package com.dossantos.melimobilecandidate.di

import com.dossantos.melimobilecandidate.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun getMainModules() = mainModules

private val mainModules by lazy {
    loadKoinModules(listOf(viewModelModule))
}

private val viewModelModule = module {
    viewModel { HomeViewModel() }
}
