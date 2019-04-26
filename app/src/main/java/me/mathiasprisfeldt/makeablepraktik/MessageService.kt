package me.mathiasprisfeldt.makeablepraktik

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MessageService(private val user: String) {
    private val db = FirebaseFirestore.getInstance()

    public val messages = db.collection("messages").orderBy("date", Query.Direction.ASCENDING)

    fun send(text: String) {
        val msg = Message(
            text,
            user,
            Timestamp.now()
        )

        db.collection("messages").document().set(msg)
    }
}