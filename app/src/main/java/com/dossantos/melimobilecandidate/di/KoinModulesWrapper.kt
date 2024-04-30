package com.dossantos.melimobilecandidate.di

import androidx.room.Room
import com.dossantos.data.database.MeLiSuggestionDataBase
import com.dossantos.data.database.MeLiSuggestionsDao
import com.dossantos.data.network.category.MeLiCategoryEndpoint
import com.dossantos.data.network.info.MeLiBrInfoEndpoint
import com.dossantos.data.network.search.MeLiSearchEndpoint
import com.dossantos.data.repository.category.CategoryRepositoryImpl
import com.dossantos.data.repository.offer.OfferRepositoryImpl
import com.dossantos.data.repository.suggestions.SuggestionsRepositoryImpl
import com.dossantos.domain.repository.category.CategoryRepository
import com.dossantos.domain.repository.offer.OfferRepository
import com.dossantos.domain.repository.suggestions.SuggestionsRepository
import com.dossantos.domain.usecase.category.CategoryMenuUseCase
import com.dossantos.domain.usecase.offer.OfferUseCase
import com.dossantos.domain.usecase.suggestions.SuggestionsBusiness
import com.dossantos.melimobilecandidate.viewmodel.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun getMainModules() = mainModules

private val mainModules by lazy {
    loadKoinModules(listOf(repositoryModules, useCaseModules, viewModelModules, extraModules))
}

private val viewModelModules = module {
    viewModel {
        HomeViewModel(
            offerUseCase = get(),
            categoryMenuUseCase = get(),
            suggestionsBusiness = get()
        )
    }
}

private val useCaseModules = module {
    single { OfferUseCase(offerRepository = get()) }
    single { CategoryMenuUseCase(categoryRepository = get()) }
    single { SuggestionsBusiness(suggestionsRepository = get()) }
}

private val repositoryModules = module {
    single<OfferRepository> { OfferRepositoryImpl() }

    single<CategoryRepository> {
        CategoryRepositoryImpl(
            categoryEndpoint = MeLiCategoryEndpoint.instance,
            infoEndpoint = MeLiBrInfoEndpoint.instance
        )
    }

    single<SuggestionsRepository> {
        SuggestionsRepositoryImpl(
            suggestionsDao = get(),
            searchEndpoint = MeLiSearchEndpoint.instance
        )
    }
}

private val extraModules = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            MeLiSuggestionDataBase::class.java,
            "meli_suggestions"
        ).build()
    }

    single<MeLiSuggestionsDao> { get<MeLiSuggestionDataBase>().getSuggestionsDao() }
}
