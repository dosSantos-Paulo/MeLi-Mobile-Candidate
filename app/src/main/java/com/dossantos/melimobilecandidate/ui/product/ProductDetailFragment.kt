package com.dossantos.melimobilecandidate.ui.product

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.dossantos.designsystem.model.product.MeLiProductModel
import com.dossantos.melimobilecandidate.databinding.FragmentProductDetailBinding
import com.dossantos.melimobilecandidate.ui.base.BaseFragment
import com.dossantos.melimobilecandidate.ui.home.SearchInterface
import com.dossantos.melimobilecandidate.utils.Integers.maxLength
import com.dossantos.melimobilecandidate.utils.Long.oneSecond
import com.dossantos.melimobilecandidate.viewmodel.product.ProductDetailStateUi
import com.dossantos.melimobilecandidate.viewmodel.product.ProductDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>() {

    private val viewModel: ProductDetailViewModel by viewModel {
        parametersOf(arguments?.getString(SEARCH_PRODUCT, String()))
    }

    private var retryPossibility = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
        setupObservables()
        setupToolbar()
    }

    private fun setupToolbar() {
        (activity as? SearchInterface)?.getToolbarView()?.let { toolbar ->
            toolbar.setup(
                backButtonVisibility = true,
                cancelButtonVisibility = false,
                cartButtonVisibility = true,
                searchBoxVisibility = false,
                isFixed = true
            )
            toolbar.setOnBackButtonClickListener {
                toolbar.setup(isDefaultSetup = true)
                toolbar.clearTextField()
                findNavController().popBackStack()
            }
        }
    }

    private fun setupObservables() {
        viewModel.retryPossibility.observe(viewLifecycleOwner) { retryPossibility = it }

        viewModel.productDetailStateUi.observe(viewLifecycleOwner)
        { observer -> observer.uiState?.let { stateUi -> onProductDetail(stateUi) } }
    }

    private fun onProductDetail(stateUi: ProductDetailStateUi.StateUi) = when (stateUi) {
        is ProductDetailStateUi.StateUi.OnSuccess -> {
            binding.mainLayout.isVisible = true
            binding.includedLayout.isVisible = false
            binding.errorView.root.isVisible = false
            binding.loadingView.root.isVisible = false
            setupViews(stateUi.product)
        }

        is ProductDetailStateUi.StateUi.ShowLoading -> {
            binding.mainLayout.isVisible = false
            binding.includedLayout.isVisible = true
            binding.errorView.root.isVisible = false
            binding.loadingView.root.isVisible = true
        }

        is ProductDetailStateUi.StateUi.OnError -> {
            binding.mainLayout.isVisible = false
            binding.includedLayout.isVisible = true
            binding.errorView.root.isVisible = true
            binding.loadingView.root.isVisible = false
        }
    }

    private fun setupViews(product: MeLiProductModel) {
        product.productDescription?.let {
            if (it.length > maxLength) binding.buyButton2.isVisible = true
        }

        product.picturesUrl?.let { pic ->
            activity?.let { binding.meLiImageCarousel.setup(pic, it) }
        }

        binding.title.text = product.title
        binding.originalPrice.text = product.originalPrice
        binding.price.text = product.price
        binding.discount.text = product.discountAmount
        binding.description.text = product.productDescription
        binding.warranty.text = product.warranty

        binding.buyButton.setOnClickListener { onBuyButtonClick() }
        binding.buyButton2.setOnClickListener { onBuyButtonClick() }

        binding.shareButton.setOnClickListener {
            product.permalink?.let { it1 -> shareText(it1) }
        }

        binding.errorView.retryButton.setOnClickListener {
            binding.mainLayout.isVisible = false
            binding.includedLayout.isVisible = false
            binding.errorView.root.isVisible = false
            binding.loadingView.root.isVisible = false
            viewModel.retryProductDetail()
        }
    }

    private fun onBuyButtonClick() {
        binding.mainLayout.isVisible = false
        binding.includedLayout.isVisible = true
        binding.loadingView.root.isVisible = true
        Handler(Looper.getMainLooper()).postDelayed({
            binding.loadingView.root.isVisible = false
            binding.errorView.root.isVisible = true
        }, oneSecond)
    }

    private fun shareText(text: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(intent, "Compartilhar via"))
    }

    companion object {
        //Deeplink or navigation
        const val SEARCH_PRODUCT = "productId"
    }
}
