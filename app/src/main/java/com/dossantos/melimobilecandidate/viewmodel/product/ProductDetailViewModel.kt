package com.dossantos.melimobilecandidate.viewmodel.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dossantos.data.utils.Numbers.Integers.four
import com.dossantos.designsystem.model.product.MeLiProductModel
import com.dossantos.domain.model.product.ProductResponseModel
import com.dossantos.domain.usecase.products.ProductsUseCase
import com.dossantos.melimobilecandidate.utils.Integers.zero
import com.dossantos.melimobilecandidate.utils.runOnIO
import com.dossantos.melimobilecandidate.utils.singleOrThrow
import timber.log.Timber

class ProductDetailViewModel(
    private val productId: String,
    private val productsUseCase: ProductsUseCase
) : ViewModel() {

    private val maxRetry = four
    private var productDetailRetry = zero

    private val _productDetailStateUi = MutableLiveData<ProductDetailStateUi>()
    val productDetailStateUi: LiveData<ProductDetailStateUi>
        get() = _productDetailStateUi

    private val _retryPossibility = MutableLiveData(true)
    val retryPossibility: LiveData<Boolean>
        get() = _retryPossibility

    fun init() {
        if (_productDetailStateUi.value == null) getProductsById()
    }

    fun retryProductDetail() {
        productDetailRetry++
        if (productDetailRetry <= maxRetry) getProductsById()
        else _retryPossibility.postValue(false)
    }


    private fun getProductsById() = runOnIO {
        _productDetailStateUi.postValue(ProductDetailStateUi().onLoading())
        productsUseCase.getProductById(productId)
            .singleOrThrow(::onProductSuccess, ::onProductError)
    }

    private fun onProductSuccess(product: ProductResponseModel) {
        _productDetailStateUi.postValue(ProductDetailStateUi().onSuccess(product.toMeLiModel()))
    }

    private fun onProductError(ex: Exception) {
        Timber.e(ex)
        _productDetailStateUi.postValue(ProductDetailStateUi().onError())
    }

    private fun ProductResponseModel.toMeLiModel() = MeLiProductModel(
        id = id,
        title = title,
        categoryId = categoryId,
        price = price,
        originalPrice = originalPrice,
        discountAmount = discountAmount,
        currencyId = currencyId,
        permalink = permalink,
        picturesUrl = picturesUrl?.map { it ?: String() },
        warranty = warranty,
        productDescription = productDescription
    )
}
