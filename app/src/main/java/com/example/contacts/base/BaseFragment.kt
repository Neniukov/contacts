package com.example.contacts.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.contacts.common.InAppFragmentDialog
import com.example.contacts.common.ProgressFragmentDialog
import com.example.contacts.extensions.launchWhenStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach

abstract class BaseFragment<ViewBindingType : ViewBinding> : Fragment() {

    protected abstract val layoutRes: Int
    protected abstract val viewModel: BaseViewModel
    protected abstract val navigator: BaseNavigator

    protected val binding: ViewBindingType
        get() = requireNotNull(bindingNullable)
    private var bindingNullable: ViewBindingType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator.attachFragmentManager(parentFragmentManager)

        viewModel.progressVisible.onEach {
            if (it) ProgressFragmentDialog.showDialog(parentFragmentManager)
            else ProgressFragmentDialog.hide(parentFragmentManager)
        }.launchWhenStarted(lifecycleScope)
        errorAction {}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingNullable = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    protected open fun initViews() = Unit

    fun errorAction(action: () -> Unit) {
        viewModel.errorFlow
            .filterNotNull()
            .onEach {
                InAppFragmentDialog.showError(it, requireActivity())
                action.invoke()
            }.launchWhenStarted(lifecycleScope)
    }

    override fun onDestroyView() {
        bindingNullable = null
        super.onDestroyView()
    }

    protected fun isDestroyedView() = bindingNullable == null
}