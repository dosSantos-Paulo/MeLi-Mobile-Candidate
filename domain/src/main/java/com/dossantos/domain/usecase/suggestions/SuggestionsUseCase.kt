package com.dossantos.domain.usecase.suggestions

import com.dossantos.domain.repository.suggestions.SuggestionsRepository

class SuggestionsBusiness(private val suggestionsRepository: SuggestionsRepository) {
    fun getSuggestions() = suggestionsRepository.getSuggestions()
    fun addSuggestions(categoryId: String) = suggestionsRepository.addSuggestion(categoryId)
}