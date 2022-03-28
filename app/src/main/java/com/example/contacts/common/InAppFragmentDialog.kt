package com.example.contacts.common

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.example.contacts.R
import kotlinx.android.synthetic.main.fragment_notification.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class InAppFragmentDialog : DialogFragment() {

    private val title: String?
        get() = arguments?.getString(TITLE)


    override fun onAttach(context: Context) {
        super.onAttach(context)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.attributes?.y = 100
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_notification, null)
        builder.setView(view)
        val dialog = builder.create()
        dialog.window?.setGravity(Gravity.TOP)
        view.tvTitle.text = title
        dialog.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        )
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dismiss()
                activity?.onBackPressed()
                true
            } else
                false
        }
        lifecycleScope.launchWhenStarted {
            flow { emit(Unit) }
                .onEach { delay(NOTIFICATIONS_DURATION) }
                .onEach { dismiss() }
                .collect()
        }
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, 0)
    }

    override fun onPause() {
        super.onPause()
        dialog?.setOnKeyListener { _, _, _ -> true }
        dismiss()
    }

    companion object {
        private const val TITLE = "title"
        private const val NOTIFICATIONS_DURATION: Long = 3000

        @JvmStatic
        fun showError(text: String?, activity: Activity) {
            val fragmentManager = (activity as? FragmentActivity)?.supportFragmentManager ?: return
            InAppFragmentDialog().apply {
                arguments = bundleOf(TITLE to text)
            }.also { dismissAndShow(it, fragmentManager) }
        }

        private fun dismissAndShow(dialog: InAppFragmentDialog, fragmentManager: FragmentManager) {
            (fragmentManager.findFragmentByTag(InAppFragmentDialog::class.java.name) as? InAppFragmentDialog)?.dismiss()
            dialog.show(fragmentManager, InAppFragmentDialog::class.java.name)
        }
    }
}