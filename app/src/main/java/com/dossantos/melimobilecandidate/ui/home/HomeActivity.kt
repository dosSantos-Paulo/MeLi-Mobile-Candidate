package com.dossantos.melimobilecandidate.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.dossantos.melimobilecandidate.databinding.ActivityHomeBinding
import com.dossantos.melimobilecandidate.ui.base.BaseActivity
import com.dossantos.melimobilecandidate.utils.ElseNothing
import org.koin.android.ext.android.get

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init()
        viewModel.stateUi.observe(this) { stateUi ->
            when (val currentState = stateUi.currentState) {
                is HomeStateUi.UiState.OnError -> {
                    binding.progressCircular.isVisible = false
                    binding.text.isVisible = true
                    binding.text.text = currentState.message
                }
                is HomeStateUi.UiState.OnSuccess -> {
                    binding.progressCircular.isVisible = false
                    binding.text.isVisible = true
                    binding.text.text = currentState.message
                }
                is HomeStateUi.UiState.ShowLoading -> {
                    binding.progressCircular.isVisible = true
                    binding.text.isVisible = false
                }

                null -> ElseNothing
            }
        }
    }
}
