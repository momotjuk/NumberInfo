package com.numberinfo.core.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard(focusView: View? = null) {
    activity?.hideKeyboard(focusView ?: view)
}

fun Activity.hideKeyboard(view: View? = null) {
    val inputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    try {
        (
                if (window.decorView.findFocus() != null) {
                    window.decorView.windowToken
                } else {
                    view?.windowToken
                }
                )?.let { windowToken ->
                inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
            }
    } catch (ex: Exception) {
    }
}

fun Fragment.showKeyboard(view: View) {
    requireContext().apply {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        try {
            view.requestFocus()
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        } catch (ex: Exception) {
        }
    }
}

fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_LONG) {
    requireContext().toast(message, duration)
}

fun Context.toast(message: String, duration: Int = Toast.LENGTH_LONG): Toast =
    Toast.makeText(this, message, duration).apply { show() }
