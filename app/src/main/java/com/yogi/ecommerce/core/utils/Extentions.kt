package com.yogi.ecommerce.core.utils

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager


fun Context.showKeyboard() {
    val inputMethodManager =
        this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}