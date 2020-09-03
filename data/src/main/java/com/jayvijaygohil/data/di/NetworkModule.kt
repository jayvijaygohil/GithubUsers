package com.jayvijaygohil.data.di

import com.jayvijaygohil.data.network.GithubService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val koinNetworkModule = module {
    single { OkHttpClient.Builder() }
    single { get<OkHttpClient.Builder>().build() }
    single {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
    single { get<Retrofit>().create(GithubService::class.java) }
}