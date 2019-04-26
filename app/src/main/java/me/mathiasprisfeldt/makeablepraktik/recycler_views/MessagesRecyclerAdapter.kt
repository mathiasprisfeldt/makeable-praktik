package me.mathiasprisfeldt.makeablepraktik.recycler_views

import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import me.mathiasprisfeldt.makeablepraktik.Message
import me.mathiasprisfeldt.makeablepraktik.R

private class MessagesRecyclerAdapter internal constructor(options: FirestoreRecyclerOptions<Message>) : FirestoreRecyclerAdapter<Message, MessageHolder>(options) {
    override fun onBindViewHolder(productViewHolder: MessageHolder, position: Int, productModel: Message) {
        productViewHolder.setModel(productModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_message, parent, false)
        return MessageHolder(view)
    }
}