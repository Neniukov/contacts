package com.example.contacts.feature.user.details

import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.example.contacts.R
import com.example.contacts.base.BaseFragment
import com.example.contacts.databinding.FragmentUserDetailsBinding
import com.example.contacts.extensions.launchWhenStarted
import com.example.contacts.extensions.loadCircleImage
import com.example.contacts.extensions.openUrl
import com.example.contacts.extensions.visibleIfOrGone
import com.example.contacts.feature.user.UserNavigator
import com.example.domain.user.model.UserDetails
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailsFragment : BaseFragment<FragmentUserDetailsBinding>() {

    override val layoutRes = R.layout.fragment_user_details
    override val viewModel: UserDetailsViewModel by viewModel()
    override val navigator: UserNavigator = get()

    override fun initViews() {
        super.initViews()
        binding.ivBack.setOnClickListener { navigator.back() }
        arguments?.getString(USER)?.let(viewModel::getUserDetails)
        viewModel.user
            .filterNotNull()
            .onEach(::fetchUser)
            .launchWhenStarted(lifecycleScope)
        errorAction { showViews(true) }
    }

    private fun fetchUser(user: UserDetails?) {
        showViews(user == null)
        user?.apply {
            binding.tvTitleName.text = name
            binding.ivAvatar.loadCircleImage(avatarUrl)
            binding.tvName.text = name
            binding.tvLink.text = blog
            binding.tvRepos.text = getString(R.string.repos, publicRepos.toString())
            binding.tvGists.text = getString(R.string.repos, publicGists.toString())
            binding.tvFollowers.text = getString(R.string.repos, followers.toString())
            binding.tvLink.setOnClickListener { requireActivity().openUrl(blog) }
        }
    }

    private fun showViews(isError: Boolean) {
        binding.ivWarning.visibleIfOrGone { isError }
        binding.llUserDetails.visibleIfOrGone { !isError }
    }

    companion object {
        private const val USER = "USER"
        fun newInstance(userName: String) = UserDetailsFragment().apply {
            arguments = bundleOf(USER to userName)
        }
    }
}