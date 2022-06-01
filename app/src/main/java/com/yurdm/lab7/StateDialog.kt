package com.yurdm.lab7

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class StateDialog(
    private val title: String = "Alert title",
    private val text: String = ""
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireActivity())
            .setTitle(title)
            .setMessage(text)
            .setPositiveButton("Ok", null)
            .create()
    }
}