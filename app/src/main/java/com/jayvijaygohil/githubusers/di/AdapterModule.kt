package com.jayvijaygohil.githubusers.di

import com.jayvijaygohil.githubusers.common.ItemClickListener
import com.jayvijaygohil.githubusers.feature.user_info.recyclerview.RepositoryAdapter
import org.koin.dsl.module

val koinAdapterModule = module {
    factory { (clickListener: ItemClickListener) -> RepositoryAdapter(clickListener) }
}