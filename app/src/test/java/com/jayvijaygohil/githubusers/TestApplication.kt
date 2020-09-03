package com.jayvijaygohil.githubusers

import android.app.Application
import coil.Coil
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setTheme(R.style.Theme_MaterialComponents)

        startKoin {
            androidContext(this@TestApplication)
            modules(module { getFakeSuccessfullImageLoader() })
        }

        Coil.setImageLoader(getFakeSuccessfullImageLoader())
    }
}