package me.mathiasprisfeldt.makeablepraktik

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class MessageService(private val user: String) {
    private val db = FirebaseFirestore.getInstance()

    fun send(text: String) {
        val msg = Message(
            text,
            user,
            Timestamp.now()
        )

        db.collection("messages").document().set(msg)
    }
}