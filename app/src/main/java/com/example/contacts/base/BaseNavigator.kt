package com.example.contacts.base

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.contacts.R
import com.example.contacts.extensions.animateExit
import com.example.contacts.extensions.animateShared
import java.lang.ref.WeakReference

abstract class BaseNavigator {

    var fragmentManager: WeakReference<FragmentManager?> = WeakReference(null)

    fun attachFragmentManager(fragmentManager: FragmentManager?) {
        this.fragmentManager = WeakReference(fragmentManager)
    }

    fun release() {
        fragmentManager.clear()
    }

    fun back() {
        fragmentManager.get()?.popBackStack()
    }

    fun FragmentManager.goWithAnimation(targetFragment: Fragment, vararg sharedViews: View) {
        val previousFragment = findFragmentById(R.id.container) ?: return
        previousFragment.animateExit()
        targetFragment.animateShared(previousFragment.requireContext())
        beginTransaction()
            .replace(R.id.container, targetFragment)
            .apply { sharedViews.forEach { addSharedElement(it, it.transitionName) } }
            .commit()
    }

    fun FragmentManager.goWithAnimationAndBack(targetFragment: Fragment, vararg sharedViews: View) {
        val previousFragment = findFragmentById(R.id.container) ?: return
        previousFragment.animateExit()
        targetFragment.animateShared(previousFragment.requireContext())
        beginTransaction()
            .add(R.id.container, targetFragment)
            .apply { sharedViews.forEach { addSharedElement(it, it.transitionName) } }
            .addToBackStack(targetFragment::class.java.name)
            .commit()

    }
}