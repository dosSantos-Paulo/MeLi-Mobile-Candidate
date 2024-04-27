package com.dossantos.melimobilecandidate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dossantos.melimobilecandidate.databinding.ActivityMainBinding
//Only to run new ci workflow

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}
