package me.mathiasprisfeldt.makeablepraktik.extensions

import android.widget.TextView

fun TextView.validateNotEmpty(errorMsg: String? = null): Pair<String, Boolean> {
    val value = this.text.toString()

    if (value.isEmpty()){
        this.error = errorMsg ?: "$hint cant be empty"
        return "" to false
    }

    return value to true
}