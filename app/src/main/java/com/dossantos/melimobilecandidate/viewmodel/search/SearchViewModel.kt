package com.dossantos.melimobilecandidate.viewmodel.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dossantos.data.utils.Numbers.Integers.four
import com.dossantos.domain.model.search.SearchResponseModel
import com.dossantos.domain.usecase.search.SearchUseCase
import com.dossantos.melimobilecandidate.model.home.toMeLiProduct
import com.dossantos.melimobilecandidate.utils.Integers.zero
import com.dossantos.melimobilecandidate.utils.runOnIO
import com.dossantos.melimobilecandidate.utils.singleOrThrow

class SearchViewModel(
    private val searchUseCase: SearchUseCase,
) : ViewModel() {

    private var currentSearch = String()
    private var pageIndex = zero
    private var maxRetry = four
    private var searchRetry = zero

    private val _searchStateUi = MutableLiveData<SearchStateUi>()
    val searchStateUi: LiveData<SearchStateUi>
        get() = _searchStateUi

    private val _retryPossibility = MutableLiveData(true)
    val retryPossibility: LiveData<Boolean>
        get() = _retryPossibility

    fun initSearch(string: String) {
        currentSearch = string
        searchRetry = zero
        pageIndex = zero
        meLiSearch()
    }

    fun tryNextPage() {
        pageIndex++
        meLiSearch()
    }

    fun retrySearch() {
        searchRetry++
        if (searchRetry <= maxRetry) meLiSearch()
        else _retryPossibility.postValue(false)
    }

    private fun meLiSearch() = runOnIO {
        _searchStateUi.postValue(SearchStateUi().onLoading())
        _searchStateUi.postValue(SearchStateUi().onLoading())
        searchUseCase.searchProductByString(currentSearch, pageIndex)
            .singleOrThrow(::onSearchSuccess, ::onError)
    }

    private fun onSearchSuccess(response: SearchResponseModel) {
        response.products?.let { products ->
            val newResponse = products.map { it.toMeLiProduct() }
            _searchStateUi.postValue(SearchStateUi().onSuccess(newResponse))
        }
    }

    private fun onError(ex: Exception) {
        ex.printStackTrace()
        _searchStateUi.postValue(SearchStateUi().onError())
    }
}
