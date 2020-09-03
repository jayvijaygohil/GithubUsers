package com.jayvijaygohil.githubusers.di

import com.jayvijaygohil.githubusers.feature.user_info.UserInfoViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinViewModelModule = module {
    viewModel { UserInfoViewModel(get(), get()) }
}