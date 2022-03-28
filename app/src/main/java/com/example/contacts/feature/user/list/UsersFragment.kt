package com.example.contacts.feature.user.list

import androidx.lifecycle.lifecycleScope
import com.example.contacts.R
import com.example.contacts.base.BaseAdapter
import com.example.contacts.base.BaseFragment
import com.example.contacts.databinding.FragmentUsersBinding
import com.example.contacts.extensions.launchWhenStarted
import com.example.contacts.extensions.visibleIfOrGone
import com.example.contacts.feature.user.UserNavigator
import com.example.contacts.feature.user.list.adapter.UserHolder
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersFragment : BaseFragment<FragmentUsersBinding>() {

    override val layoutRes = R.layout.fragment_users
    override val viewModel: UsersViewModel by viewModel()
    override val navigator: UserNavigator = get()

    private val adapter = BaseAdapter()
        .map(R.layout.item_user, UserHolder(navigator::goToUserDetails))

    override fun initViews() {
        super.initViews()
        binding.rvUsers.adapter = adapter
        viewModel.users.onEach { users ->
            if (!users.isNullOrEmpty()) adapter.itemsLoaded(users)
            showViews(false)
        }.launchWhenStarted(lifecycleScope)
        errorAction { showViews(true) }
        binding.srRefresh.setOnRefreshListener { viewModel.getUsers() }
    }

    private fun showViews(isError: Boolean) {
        binding.ivWarning.visibleIfOrGone { isError }
        binding.rvUsers.visibleIfOrGone { !isError }
        binding.srRefresh.isRefreshing = false
    }

    companion object {
        fun newInstance() = UsersFragment()
    }
}