package com.dossantos.domain.usecase.search

import com.dossantos.domain.repository.search.SearchRepository

class SearchUseCase(private val searchRepository: SearchRepository) {
    fun search(string: String, offset: Int) =
        if (Regex("^[A-Z]{3}\\d+$").matches(string)) searchRepository.searchByCategory(string)
        else searchRepository.searchByString(string, offset)
}
