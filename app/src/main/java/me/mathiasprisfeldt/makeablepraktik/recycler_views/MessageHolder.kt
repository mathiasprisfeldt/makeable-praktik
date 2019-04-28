package me.mathiasprisfeldt.makeablepraktik.recycler_views

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import me.mathiasprisfeldt.makeablepraktik.types.Message
import me.mathiasprisfeldt.makeablepraktik.R

class MessageHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {
    private var savedDateVisibility: Int = View.INVISIBLE

    internal fun setModel(data: Message) {
        val txt = view.findViewById<TextView>(R.id.message_text)
        txt.text = data.msg

        val author = view.findViewById<TextView>(R.id.message_author)
        author?.text = data.from

        view.findViewById<TextView>(R.id.message_date)?.apply {
            text = data.dateFormatted()
            savedDateVisibility = visibility

            txt.setOnFocusChangeListener { _, hasFocus ->
                visibility = if (hasFocus) {
                    this.animate().alpha(1.0f)
                    View.VISIBLE
                } else {
                    this.animate().alpha(0.0f)
                    savedDateVisibility
                }

            }
        }

    }
}