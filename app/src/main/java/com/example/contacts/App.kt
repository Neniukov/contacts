package com.example.contacts

import android.app.Application
import com.example.contacts.di.navigatorModule
import com.example.contacts.di.viewModelModule
import com.example.data.di.dataModule
import com.example.domain.di.domainModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(viewModelModule, navigatorModule, domainModule, dataModule))
    }
}