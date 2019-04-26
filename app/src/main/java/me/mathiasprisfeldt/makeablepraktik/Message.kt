package me.mathiasprisfeldt.makeablepraktik

import com.google.firebase.Timestamp

data class Message(
    val msg: String = "unknown",
    val from: String = "unknown",
    val date: Timestamp = Timestamp.now()
)