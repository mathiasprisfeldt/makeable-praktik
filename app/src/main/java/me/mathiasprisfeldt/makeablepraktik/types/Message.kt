package me.mathiasprisfeldt.makeablepraktik.types

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import me.mathiasprisfeldt.makeablepraktik.extensions.format

data class Message(
    val msg: String = "unknown",
    val from: String = "unknown",
    val date: Timestamp = Timestamp.now()
) {
    val authorText: String
        @Exclude get() = "$from - ${date.format()}"
}