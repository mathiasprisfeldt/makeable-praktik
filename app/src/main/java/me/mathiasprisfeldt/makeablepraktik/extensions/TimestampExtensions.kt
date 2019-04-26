package me.mathiasprisfeldt.makeablepraktik.extensions

import android.text.format.DateFormat
import com.google.firebase.Timestamp

fun Timestamp.format(): String {
    return DateFormat.format("dd-MM-yyyy hh:mm:ss", this.toDate()).toString()
}