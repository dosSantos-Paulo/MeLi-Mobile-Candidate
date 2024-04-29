package com.dossantos.melimobilecandidate.di

import com.dossantos.data.repository.offer.OfferRepositoryImpl
import com.dossantos.domain.offer.OfferRepository
import com.dossantos.domain.offer.OfferUseCase
import com.dossantos.melimobilecandidate.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun getMainModules() = mainModules

private val mainModules by lazy {
    loadKoinModules(listOf(repositoryModules, useCaseModules, viewModelModules))
}

private val viewModelModules = module {
    viewModel { HomeViewModel(offerUseCase = get()) }
}

private val useCaseModules = module {
    single { OfferUseCase(offerRepository = get()) }
}

private val repositoryModules = module {
    single<OfferRepository> { OfferRepositoryImpl() }
}
