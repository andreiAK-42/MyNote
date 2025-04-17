package com.example.mynote.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DialogFragmentSetTags(tags: Array<String>, checkedItems: BooleanArray) : DialogFragment() {
    var tags = tags
    var checkedItems = checkedItems
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Select tags")
                .setMultiChoiceItems(
                    tags, checkedItems
                ) { _, which, isChecked ->
                    checkedItems[which] = isChecked
                }
                .setPositiveButton(
                    "Ok"
                ) { _, _ ->
                    for (i in tags.indices) {
                        val checked = checkedItems[i]
                        if (checked) {
                            println(tags[i])
                        }
                    }
                }
                .setNegativeButton(
                    "Cancell"
                ) { dialog, _ ->
                    dialog.cancel()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}