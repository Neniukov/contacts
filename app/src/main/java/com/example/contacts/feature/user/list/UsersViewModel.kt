package com.example.contacts.feature.user.list

import com.example.contacts.base.BaseViewModel
import com.example.domain.user.model.User
import com.example.domain.user.usecase.UserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsersViewModel(private val userUseCase: UserUseCase) : BaseViewModel() {

    private val _users = MutableStateFlow(listOf<User>())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    init {
        getUsers()
    }

    fun getUsers() = launch {
        userUseCase.getUsers()
            .invokeWithDialog()
            .collectWithDialog { _users.value = it }
    }
}