package com.example.mynote.ui

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import viewModel.MainActivityViewModel

class ChipManager(val chipGroup: ChipGroup, val context: Context, val viewModel: MainActivityViewModel) {


    init {
        addChip("All (${viewModel.allNoteList.value!!.count()})")
        addChip("Important (0)", Color.parseColor("#8BD2C9"))
        addChip(
            "To-Do (${viewModel.allNoteList.value!!.count()})",
            Color.parseColor("#F4D798")
        )

       addChip("Reminder (0)", Color.parseColor("#F6A5A0"))
    }

    fun addChip(
        text: String,
        backgroundColor: Int = Color.TRANSPARENT,
        cornerRadius: Float = 20f,
        showCloseIcon: Boolean = false
    ) {
        val chip = Chip(context).apply {
            this.text = text
            setTextColor(Color.BLACK)
            chipBackgroundColor = ColorStateList.valueOf(backgroundColor)
            chipStrokeWidth = 1f
            chipStrokeColor = ColorStateList.valueOf(Color.BLACK)

            shapeAppearanceModel = ShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED, cornerRadius)
                .build()

            isCloseIconVisible = showCloseIcon
            if (showCloseIcon) {
                setOnCloseIconClickListener {
                    chipGroup.removeView(this)
                }
            }
        }
        chipGroup.addView(chip)
    }

    fun clearChips() {
        chipGroup.removeAllViews()
    }

    fun updateChip(position: Int, newText: String) {
        if (position < chipGroup.childCount) {
            (chipGroup.getChildAt(position) as? Chip)?.text = newText
        }
    }

    fun getChipsCount(): Int = chipGroup.childCount

    fun getChipText(position: Int): String? {
        return if (position < chipGroup.childCount) {
            (chipGroup.getChildAt(position) as? Chip)?.text?.toString()
        } else {
            null
        }
    }
}