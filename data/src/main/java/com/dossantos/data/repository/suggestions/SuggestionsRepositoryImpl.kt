package com.dossantos.data.repository.suggestions

import com.dossantos.data.database.MeLiSuggestionsDao
import com.dossantos.data.model.suggestions.SuggestionsEntity
import com.dossantos.data.network.search.MeLiSearchEndpoint
import com.dossantos.data.repository.search.SearchRepositoryImpl.Companion.toModel
import com.dossantos.data.utils.Numbers.Integers.four
import com.dossantos.domain.model.suggestions.SuggestionsModel
import com.dossantos.domain.model.suggestions.SuggestionsType
import com.dossantos.domain.repository.suggestions.SuggestionsRepository
import java.util.Date
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class SuggestionsRepositoryImpl(
    private val suggestionsDao: MeLiSuggestionsDao,
    private val searchEndpoint: MeLiSearchEndpoint
) : SuggestionsRepository {

    @Suppress("TooGenericExceptionCaught")
    override fun addSuggestion(categoryId: String) {
        try {
            suggestionsDao.insertSuggestion(
                SuggestionsEntity(
                    categoryId = categoryId,
                    dateTimeMills = Date().time,
                    suggestionType = SuggestionsType.ALREADY_VISITED.toString()
                )
            )
        } catch (ex: Exception) {
            Timber.e(ex)
        }
    }

    override fun getSuggestions() = flow {
        val getDao = suggestionsDao.getSuggestions()
        val suggestions = if (getDao.isEmpty()) suggestionsMock() else getDao.toModel()
        emit(suggestions)
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
        .searchByCategory(categoryId = categoryId, limit = four).toModel().products

    private fun mockedSuggestions() = listOf("MLB1000", "MLB1574", "MLB1276", "MLB1051")
}
