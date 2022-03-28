package com.example.domain.user.repository

import com.example.domain.user.model.User
import com.example.domain.user.model.UserDetails
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUsers(): Flow<List<User>>

    suspend fun getUserDetails(login: String): Flow<UserDetails>
}