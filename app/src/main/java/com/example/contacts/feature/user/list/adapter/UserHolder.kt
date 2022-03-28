package com.example.contacts.feature.user.list.adapter

import com.example.contacts.R
import com.example.contacts.base.Holder
import com.example.contacts.databinding.ItemUserBinding
import com.example.contacts.extensions.loadCircleImage
import com.example.domain.user.model.User

class UserHolder(private val action: (User) -> Unit) : Holder<User, ItemUserBinding>() {
    override fun bind(binding: ItemUserBinding, item: User) {
        with(binding) {
            ivAvatar.loadCircleImage(item.avatarUrl, R.drawable.ic_user)
            tvName.text = item.login
            root.setOnClickListener { action.invoke(item) }
        }
    }
}