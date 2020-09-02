package com.jayvijaygohil.githubusers

import android.app.Application
import com.jayvijaygohil.githubusers.di.koinAppComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class GithubUsersApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@GithubUsersApplication)
            modules(koinAppComponent)
        }
    }
}