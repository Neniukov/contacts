package com.example.contacts.di

import com.example.contacts.feature.user.details.UserDetailsViewModel
import com.example.contacts.feature.user.list.UsersViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel { UsersViewModel(get()) }
    viewModel { UserDetailsViewModel(get()) }
}