package me.mathiasprisfeldt.makeablepraktik

import com.google.firebase.Timestamp

data class Message(
    var msg: String,
    var from: String,
    var date: Timestamp
)