package com.dossantos.melimobilecandidate.di

import com.dossantos.data.network.category.MeLiCategoryEndpoint
import com.dossantos.data.network.info.MeLiBrInfoEndpoint
import com.dossantos.data.repository.category.CategoryRepositoryImpl
import com.dossantos.data.repository.offer.OfferRepositoryImpl
import com.dossantos.domain.repository.category.CategoryRepository
import com.dossantos.domain.repository.offer.OfferRepository
import com.dossantos.domain.usecase.category.CategoryMenuUseCase
import com.dossantos.domain.usecase.offer.OfferUseCase
import com.dossantos.melimobilecandidate.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun getMainModules() = mainModules

private val mainModules by lazy {
    loadKoinModules(listOf(repositoryModules, useCaseModules, viewModelModules))
}

private val viewModelModules = module {
    viewModel {
        HomeViewModel(
            offerUseCase = get(),
            categoryMenuUseCase = get()
        )
    }
}

private val useCaseModules = module {
    single { OfferUseCase(offerRepository = get()) }
    single { CategoryMenuUseCase(categoryRepository = get()) }
}

private val repositoryModules = module {
    single<OfferRepository> { OfferRepositoryImpl() }

    single<CategoryRepository> {
        CategoryRepositoryImpl(
            categoryEndpoint = MeLiCategoryEndpoint.instance,
            infoEndpoint = MeLiBrInfoEndpoint.instance
        )
    }
}
