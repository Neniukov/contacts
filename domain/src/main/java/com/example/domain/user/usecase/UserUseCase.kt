package com.example.domain.user.usecase

import com.example.domain.user.model.User
import com.example.domain.user.model.UserDetails
import com.example.domain.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    suspend fun getUsers(): Flow<List<User>>
    suspend fun getUserDetails(login: String): Flow<UserDetails>
}

class UserUseCaseImpl(private val repository: UserRepository) : UserUseCase {

    override suspend fun getUsers() = repository.getUsers()

    override suspend fun getUserDetails(login: String) = repository.getUserDetails(login)

}