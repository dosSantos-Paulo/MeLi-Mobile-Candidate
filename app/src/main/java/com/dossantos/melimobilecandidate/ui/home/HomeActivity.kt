package com.dossantos.melimobilecandidate.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import com.dossantos.designsystem.ui.toast.meLiToast
import com.dossantos.melimobilecandidate.databinding.ActivityHomeBinding
import com.dossantos.melimobilecandidate.ui.base.BaseActivity

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}
