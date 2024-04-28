package com.dossantos.melimobilecandidate.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.dossantos.designsystem.toast.meLiToast
import com.dossantos.melimobilecandidate.databinding.ActivityHomeBinding
import com.dossantos.melimobilecandidate.ui.base.BaseActivity
import com.dossantos.melimobilecandidate.utils.ElseNothing
import org.koin.android.ext.android.get

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.toolbar.onSearch {
            meLiToast(it)
        }
    }
}
