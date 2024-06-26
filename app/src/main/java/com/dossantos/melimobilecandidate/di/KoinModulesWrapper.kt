package com.dossantos.melimobilecandidate.di

import androidx.room.Room
import com.dossantos.data.database.MeLiSuggestionDataBase
import com.dossantos.data.database.MeLiSuggestionsDao
import com.dossantos.data.network.category.MeLiCategoryEndpoint
import com.dossantos.data.network.info.MeLiBrInfoEndpoint
import com.dossantos.data.network.product.MeLiProductEndpoint
import com.dossantos.data.network.search.MeLiSearchEndpoint
import com.dossantos.data.repository.category.CategoryRepositoryImpl
import com.dossantos.data.repository.offer.OfferRepositoryImpl
import com.dossantos.data.repository.product.ProductRepositoryImpl
import com.dossantos.data.repository.search.SearchRepositoryImpl
import com.dossantos.data.repository.suggestions.SuggestionsRepositoryImpl
import com.dossantos.domain.repository.category.CategoryRepository
import com.dossantos.domain.repository.offer.OfferRepository
import com.dossantos.domain.repository.product.ProductRepository
import com.dossantos.domain.repository.search.SearchRepository
import com.dossantos.domain.repository.suggestions.SuggestionsRepository
import com.dossantos.domain.usecase.category.CategoryMenuUseCase
import com.dossantos.domain.usecase.offer.OfferUseCase
import com.dossantos.domain.usecase.products.ProductsUseCase
import com.dossantos.domain.usecase.search.SearchUseCase
import com.dossantos.domain.usecase.suggestions.SuggestionsUseCase
import com.dossantos.melimobilecandidate.viewmodel.home.HomeViewModel
import com.dossantos.melimobilecandidate.viewmodel.product.ProductDetailViewModel
import com.dossantos.melimobilecandidate.viewmodel.search.SearchViewModel
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
            suggestionsUseCase = get()
        )
    }

    viewModel {
        SearchViewModel(
            searchUseCase = get()
        )
    }

    viewModel { params ->
        ProductDetailViewModel(
            productId = params.get<String>(),
            productsUseCase = get()
        )
    }
}

private val useCaseModules = module {
    factory { OfferUseCase(offerRepository = get()) }
    factory { CategoryMenuUseCase(categoryRepository = get()) }
    factory { SuggestionsUseCase(suggestionsRepository = get()) }
    factory { SearchUseCase(searchRepository = get()) }
    factory { ProductsUseCase(productRepository = get(), suggestionsRepository = get()) }
}

private val repositoryModules = module {
    factory<OfferRepository> { OfferRepositoryImpl() }

    factory<CategoryRepository> {
        CategoryRepositoryImpl(
            categoryEndpoint = MeLiCategoryEndpoint.instance,
            infoEndpoint = MeLiBrInfoEndpoint.instance
        )
    }

    factory<SuggestionsRepository> {
        SuggestionsRepositoryImpl(
            suggestionsDao = get(),
            searchEndpoint = MeLiSearchEndpoint.instance
        )
    }

    factory<SearchRepository> { SearchRepositoryImpl(searchEndpoint = MeLiSearchEndpoint.instance) }

    factory<ProductRepository> { ProductRepositoryImpl(productEndpoint = MeLiProductEndpoint.instance) }
}

private val extraModules = module {
    factory {
        Room.databaseBuilder(
            androidContext(),
            MeLiSuggestionDataBase::class.java,
            "meli_suggestions"
        ).build()
    }

    factory<MeLiSuggestionsDao> { get<MeLiSuggestionDataBase>().getSuggestionsDao() }
}
