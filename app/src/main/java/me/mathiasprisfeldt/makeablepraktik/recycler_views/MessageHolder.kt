package me.mathiasprisfeldt.makeablepraktik.recycler_views

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import me.mathiasprisfeldt.makeablepraktik.Message
import me.mathiasprisfeldt.makeablepraktik.R

class MessageHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {
    internal fun setModel(data: Message) {
        val txt = view.findViewById<TextView>(R.id.message_text)
        txt.text = data.msg
    }
}