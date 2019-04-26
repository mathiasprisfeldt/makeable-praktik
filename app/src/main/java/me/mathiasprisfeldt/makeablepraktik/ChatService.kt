package me.mathiasprisfeldt.makeablepraktik

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import me.mathiasprisfeldt.makeablepraktik.types.Message

class ChatService(
    private val user: String? = null,
    private val chatRoom: String? = null
) {
    private val db = FirebaseFirestore.getInstance()

    val messages = db.collection("chatRooms/$chatRoom/messages").orderBy("date", Query.Direction.ASCENDING)
    var chatRooms: List<String> = emptyList()

    init {
        db.collection("chatRooms").addSnapshotListener { snapshot, exception ->
            snapshot?.documents?.let {
                chatRooms = it.map { elem -> elem["name"] as String }
            }
        }
    }

    fun send(text: String) {
        user ?: return

        val msg = Message(
            text,
            user,
            Timestamp.now()
        )

        db.collection("chatRooms/$chatRoom/messages").document().set(msg)
    }

    fun isUs(msg: Message): Boolean {
        return user == msg.from
    }
}