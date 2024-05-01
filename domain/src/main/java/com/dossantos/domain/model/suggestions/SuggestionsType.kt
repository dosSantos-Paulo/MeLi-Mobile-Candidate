package com.dossantos.domain.model.suggestions

enum class SuggestionsType {
    ALREADY_VISITED, ONLY_SUGGESTION;

    companion object {
        fun find(string: String) = try {
            valueOf(string)
        } catch (_: Exception) {
            ONLY_SUGGESTION
        }
    }
}
