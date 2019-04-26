package me.mathiasprisfeldt.makeablepraktik.types

data class ChatRoom(
    val name: String = "unknown",
    val messages: List<Message> = emptyList()
)