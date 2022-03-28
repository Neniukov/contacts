package com.example.data.di

import com.example.data.retrofit.RetrofitFactory
import com.example.data.retrofit.RetrofitFactoryImpl
import com.example.data.user.api.UserApi
import com.example.data.user.repository.UserRepositoryImpl
import com.example.domain.user.repository.UserRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import retrofit2.Retrofit

val dataModule = module(override = true) {
    single<Gson> { GsonBuilder().setLenient().create() }
    single { OkHttpClient() }
    single<RetrofitFactory> { RetrofitFactoryImpl(get()) }
    single { get<RetrofitFactory>().createRetrofit(get()) }

    single { get<Retrofit>().create(UserApi::class.java) }

    single<UserRepository> { UserRepositoryImpl(get()) }
}