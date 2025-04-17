package com.example.mynote.ui

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel

class ChipManager(val chipGroup: ChipGroup, val context: Context) {
    fun addChip(
        text: String,
        backgroundColor: Int = Color.TRANSPARENT,
        cornerRadius: Float = 20f,
        showCloseIcon: Boolean = false
    ) {
        val chip = Chip(context).apply {
            // Basic chip setup
            this.text = text
            setTextColor(Color.BLACK)
            chipBackgroundColor = ColorStateList.valueOf(backgroundColor)
            chipStrokeWidth = 1f
            chipStrokeColor = ColorStateList.valueOf(Color.BLACK)

            // Modern corner radius setting
            shapeAppearanceModel = ShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED, cornerRadius)
                .build()

            // Close icon configuration
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