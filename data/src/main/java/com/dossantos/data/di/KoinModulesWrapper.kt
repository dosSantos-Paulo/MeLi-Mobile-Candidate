package com.dossantos.data.di

import org.koin.core.context.loadKoinModules

fun getDataModules() = dataModules

private val dataModules by lazy {
    loadKoinModules(listOf())
}
