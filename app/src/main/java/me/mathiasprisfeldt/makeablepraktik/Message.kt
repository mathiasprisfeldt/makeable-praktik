package me.mathiasprisfeldt.makeablepraktik

import com.google.firebase.Timestamp
import me.mathiasprisfeldt.makeablepraktik.extensions.format

data class Message(
    val msg: String = "unknown",
    val from: String = "unknown",
    val date: Timestamp = Timestamp.now()
) {
    val authorText: String
        get() = "$from - ${date.format()}"
}