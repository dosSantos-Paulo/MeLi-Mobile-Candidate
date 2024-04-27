package com.dossantos.melimobilecandidate.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<T : ViewBinding>: AppCompatActivity() {

    private var _binding: T? = null
    protected val binding: T
        get() = _binding as T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflateBinding()
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun inflateBinding() {
        _binding =
            ((javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>)
                .getMethod("inflate", LayoutInflater::class.java)
                .invoke(null, layoutInflater) as T
    }
}
