package com.example.data.user.repository

import com.example.data.user.api.UserApi
import com.example.data.user.conversions.toDomain
import com.example.data.utils.typeFlow
import com.example.domain.user.repository.UserRepository

class UserRepositoryImpl(private val api: UserApi) : UserRepository {

    override suspend fun getUsers() = typeFlow {
        api.getUsers().map { it.toDomain() }
    }

    override suspend fun getUserDetails(login: String) = typeFlow {
        api.getUserDetails(login).toDomain()
    }

}