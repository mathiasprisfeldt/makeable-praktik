package me.mathiasprisfeldt.makeablepraktik.types

data class ChatRoom(
    val messages: List<Message> = emptyList()
)