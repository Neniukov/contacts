package com.example.data.user.api

import com.example.data.user.model.response.UserDetailsResponse
import com.example.data.utils.Constants.USERS
import com.example.data.user.model.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {

    @GET(USERS)
    suspend fun getUsers(): List<UserResponse>

    @GET("$USERS/{login}")
    suspend fun getUserDetails(@Path("login") path: String): UserDetailsResponse
}