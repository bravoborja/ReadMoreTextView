package com.borjabravo.readmoretextview

import android.content.Context
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt

@ColorInt
fun getThemedColor(context: Context, @AttrRes themeResId: Int): Int {
    val a = context.obtainStyledAttributes(null, intArrayOf(themeResId))
    try {
        return a.getColor(0, 9)
    } finally {
        a.recycle()
    }
}