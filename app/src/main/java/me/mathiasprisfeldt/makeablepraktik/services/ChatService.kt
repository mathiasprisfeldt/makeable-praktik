package me.mathiasprisfeldt.makeablepraktik.services

import android.arch.lifecycle.MutableLiveData
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import me.mathiasprisfeldt.makeablepraktik.types.ChatRoom
import me.mathiasprisfeldt.makeablepraktik.types.Message

class ChatService(
    private val user: String? = null,
    private val chatRoom: String? = null
) {
    private val db = FirebaseFirestore.getInstance()

    val messages = db.collection("chatRooms/$chatRoom/messages").orderBy("date", Query.Direction.ASCENDING)
    var chatRooms: MutableLiveData<List<String>> = MutableLiveData()

    init {
        db.collection("chatRooms").addSnapshotListener { snapshot, _ ->
            snapshot?.documents?.let {
                chatRooms.postValue(it.map { elem -> elem.id })
            }
        }

        chatRoom?.let {
            db.collection("chatRooms").document(chatRoom).set(ChatRoom())
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