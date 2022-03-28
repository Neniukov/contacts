package com.example.contacts.di

import com.example.contacts.feature.user.UserNavigator
import com.example.contacts.feature.user.UserNavigatorImpl
import org.koin.dsl.module.module

val navigatorModule = module {
    factory<UserNavigator> { UserNavigatorImpl() }
}