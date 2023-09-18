package com.xoka74.chess

import androidx.fragment.app.Fragment


fun Fragment?.runOnUiThread(action: () -> Unit) {
    this ?: return
    if (!isAdded) return
    activity?.runOnUiThread(action)
}