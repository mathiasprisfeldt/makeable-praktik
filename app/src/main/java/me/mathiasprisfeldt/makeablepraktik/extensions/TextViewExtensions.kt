package me.mathiasprisfeldt.makeablepraktik.extensions

import android.widget.TextView

fun TextView.Validate(): Pair<String, Boolean> {
    val value = this.text.toString()

    if (value.isEmpty()){
        this.error = "$hint cant be empty"
        return "" to false
    }

    return value to true
}