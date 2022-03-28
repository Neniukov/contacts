package com.example.domain.di

import com.example.domain.user.usecase.UserUseCase
import com.example.domain.user.usecase.UserUseCaseImpl
import org.koin.dsl.module.module

val domainModule = module {
    factory<UserUseCase> { UserUseCaseImpl(get()) }
}