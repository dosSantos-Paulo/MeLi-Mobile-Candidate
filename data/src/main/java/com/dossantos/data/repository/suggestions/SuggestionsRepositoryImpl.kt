package com.dossantos.data.repository.suggestions

import com.dossantos.data.database.MeLiSuggestionsDao
import com.dossantos.data.model.suggestions.SuggestionsEntity
import com.dossantos.data.network.search.MeLiSearchEndpoint
import com.dossantos.data.repository.search.SearchRepositoryImpl.Companion.toModel
import com.dossantos.domain.model.suggestions.SuggestionsModel
import com.dossantos.domain.model.suggestions.SuggestionsType
import com.dossantos.domain.repository.suggestions.SuggestionsRepository
import java.util.Date
import kotlinx.coroutines.flow.flow

class SuggestionsRepositoryImpl(
    private val suggestionsDao: MeLiSuggestionsDao,
    private val searchEndpoint: MeLiSearchEndpoint
) : SuggestionsRepository {

    private val suggestionLimit = 4

    override fun addSuggestion(categoryId: String) {
        suggestionsDao.insertSuggestion(
            SuggestionsEntity(
                categoryId = categoryId,
                dateTimeMills = Date().time,
                SuggestionsType.ALREADY_VISITED.toString()
            )
        )
    }

    override fun getSuggestions() = flow {
        val suggestions = suggestionsDao.getSuggestions()
        emit(if (suggestions.isEmpty()) suggestionsMock() else suggestions.toModel())
    }

    private suspend fun suggestionsMock() = mockedSuggestions().map { categoryId ->
        SuggestionsModel(
            categoryId = categoryId,
            suggestionsType = SuggestionsType.ONLY_SUGGESTION,
            products = getProductByCategory(categoryId)
        )
    }

    private suspend fun List<SuggestionsEntity>.toModel() = map { entity ->
        getProductByCategory(entity.categoryId)?.let { product ->
            SuggestionsModel(
                categoryId = entity.categoryId,
                suggestionsType = SuggestionsType.find(entity.suggestionType),
                products = product
            )
        }

    }

    private suspend fun getProductByCategory(categoryId: String) = searchEndpoint
        .searchByCategory(categoryId = categoryId, limit = suggestionLimit).toModel().products

    private fun mockedSuggestions() = listOf("MLB1000", "MLB1574", "MLB1276", "MLB1051")
}
