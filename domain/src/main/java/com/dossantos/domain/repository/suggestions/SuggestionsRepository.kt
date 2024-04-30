package com.dossantos.domain.repository.suggestions

import com.dossantos.domain.model.suggestions.SuggestionsModel
import kotlinx.coroutines.flow.Flow

interface SuggestionsRepository {
    fun addSuggestion(categoryId: String)
    fun getSuggestions(): Flow<List<SuggestionsModel?>>
}