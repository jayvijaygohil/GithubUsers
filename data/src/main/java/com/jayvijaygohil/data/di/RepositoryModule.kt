package com.jayvijaygohil.data.di

import com.jayvijaygohil.data.repository.UserRepositoryImpl
import com.jayvijaygohil.domain.repository.UserRepository
import org.koin.dsl.module

val koinRepositoryModule = module {
    factory<UserRepository> {
        UserRepositoryImpl(get())
    }
}