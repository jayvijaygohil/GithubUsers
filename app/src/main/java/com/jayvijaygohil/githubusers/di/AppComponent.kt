package com.jayvijaygohil.githubusers.di

import com.jayvijaygohil.data.di.koinNetworkModule
import com.jayvijaygohil.data.di.koinRepositoryModule
import com.jayvijaygohil.domain.di.koinUseCaseModule

val koinAppComponent = listOf(
    koinNetworkModule,
    koinViewModelModule,
    koinUseCaseModule,
    koinRepositoryModule
)