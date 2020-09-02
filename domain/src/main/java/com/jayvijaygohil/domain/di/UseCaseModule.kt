package com.jayvijaygohil.domain.di

import com.jayvijaygohil.domain.usecase.FetchRepositoriesUseCase
import com.jayvijaygohil.domain.usecase.FetchRepositoriesUseCaseImpl
import com.jayvijaygohil.domain.usecase.SearchUserUseCase
import com.jayvijaygohil.domain.usecase.SearchUserUseCaseImpl
import org.koin.dsl.module

val koinUseCaseModule = module {
    factory<FetchRepositoriesUseCase> {
        FetchRepositoriesUseCaseImpl(get())
    }

    factory<SearchUserUseCase> {
        SearchUserUseCaseImpl(get())
    }
}