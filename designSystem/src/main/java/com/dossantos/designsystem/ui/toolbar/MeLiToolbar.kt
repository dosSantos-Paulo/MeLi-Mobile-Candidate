package com.dossantos.designsystem.ui.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.dossantos.designsystem.databinding.MeliToolbarBinding
import com.dossantos.designsystem.utils.Integers.zero
import com.dossantos.designsystem.utils.hideKeyboard

class MeLiToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: MeliToolbarBinding by lazy {
        MeliToolbarBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private var onSearch: (String) -> Unit = {}

    private var fixeButtons = false

    init {
        setDefaultButtons()
        setupTextField()
    }

    @Suppress("LongParameterList")
    fun setup(
        isDefaultSetup: Boolean = false,
        backButtonVisibility: Boolean = false,
        cancelButtonVisibility: Boolean = false,
        cartButtonVisibility: Boolean = true,
        searchBoxVisibility: Boolean = true,
        isFixed: Boolean = false
    ) {
        if (isDefaultSetup) {
            setDefaultButtons()
            clearTextField()
            cancelSearching()
            binding.searchField.visibility = VISIBLE
            fixeButtons = false
        } else {
            binding.buttonBack.isVisible = backButtonVisibility
            binding.buttonCancelSearch.isVisible = cancelButtonVisibility
            binding.buttonShoppingCart.isVisible = cartButtonVisibility
            binding.searchField.visibility = if (searchBoxVisibility) VISIBLE else INVISIBLE
            fixeButtons = isFixed
        }
    }

    fun onSearch(doOnSearch: (search: String) -> Unit) {
        onSearch = doOnSearch
    }

    fun clearTextField() = binding.searchField.editText?.apply {
        setText(String())
        hideKeyboard()
    }

    fun setOnCancelButtonClickListener(action: (View) -> Unit) {
        binding.buttonCancelSearch.setOnClickListener(action)
    }

    fun setOnBackButtonClickListener(action: (View) -> Unit) {
        binding.buttonBack.setOnClickListener(action)
    }

    fun setOnCartButtonClickListener(action: (View) -> Unit) {
        binding.buttonShoppingCart.setOnClickListener(action)
    }

    private fun setDefaultButtons() {
        setOnCancelButtonClickListener { clearTextField() }
        setOnBackButtonClickListener { clearTextField() }
        setOnCartButtonClickListener { clearTextField() }
    }

    private fun setupTextField() = with(binding.searchField) {
        editText?.doOnTextChanged { text, _, _, _ ->
            text?.length?.let { length ->
                if (fixeButtons.not()) {
                    if (length > zero) isSearching()
                    else cancelSearching()
                }
            }
        }

        editText?.setOnEditorActionListener { _, actionId, _ ->
            val search = editText?.text?.toString().orEmpty()
            val isActionSearchClicked = actionId == EditorInfo.IME_ACTION_SEARCH
            if (isActionSearchClicked && search.isNotEmpty()) {
                onSearch.invoke(search)
                editText?.hideKeyboard()
            }
            false
        }
    }

    private fun isSearching() = with(binding) {
        buttonShoppingCart.isVisible = false
        buttonBack.isVisible = true
        buttonCancelSearch.isVisible = true
        icon.isVisible = false
        addressText.isVisible = false
    }

    private fun cancelSearching() = with(binding) {
        buttonShoppingCart.isVisible = true
        buttonBack.isVisible = false
        buttonCancelSearch.isVisible = false
        icon.isVisible = true
        addressText.isVisible = true
    }
}
