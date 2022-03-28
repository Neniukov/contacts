package com.example.contacts.feature.user.details

import com.example.contacts.base.BaseViewModel
import com.example.domain.user.model.UserDetails
import com.example.domain.user.usecase.UserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserDetailsViewModel(private val userUseCase: UserUseCase) : BaseViewModel() {

    private val _user: MutableStateFlow<UserDetails?> = MutableStateFlow(null)
    val user: StateFlow<UserDetails?> = _user.asStateFlow()

    fun getUserDetails(userName: String) = launch {
        userUseCase.getUserDetails(userName)
            .invokeWithDialog()
            .collectWithDialog { _user.value = it }
    }
}