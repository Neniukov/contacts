package com.example.contacts.feature.user

import com.example.contacts.base.BaseNavigator
import com.example.contacts.feature.user.details.UserDetailsFragment
import com.example.domain.user.model.User

abstract class UserNavigator : BaseNavigator() {
    abstract fun goToUserDetails(user: User)
}

class UserNavigatorImpl : UserNavigator() {
    override fun goToUserDetails(user: User) {
        fragmentManager.get()?.goWithAnimationAndBack(
            UserDetailsFragment.newInstance(userName = user.login)
        )
    }

}