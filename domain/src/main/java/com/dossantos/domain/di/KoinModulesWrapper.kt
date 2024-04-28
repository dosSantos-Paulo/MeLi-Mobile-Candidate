package com.dossantos.domain.di

import org.koin.core.context.loadKoinModules

fun getDomainModules() = domainModules

private val domainModules by lazy {
    loadKoinModules(listOf())
}
