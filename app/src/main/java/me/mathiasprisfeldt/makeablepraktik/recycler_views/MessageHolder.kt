package me.mathiasprisfeldt.makeablepraktik.recycler_views

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.widget.Toast
import me.mathiasprisfeldt.makeablepraktik.types.Message
import me.mathiasprisfeldt.makeablepraktik.R
import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.support.v4.content.ContextCompat.getSystemService



class MessageHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {
    private var savedDateVisibility: Int = View.INVISIBLE

    internal fun setModel(data: Message, context: Context) {
        val txt = view.findViewById<TextView>(R.id.message_text)
        txt.text = data.msg
        txt.setOnLongClickListener {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
            val clip = ClipData.newPlainText("Message", data.msg)
            clipboard!!.primaryClip = clip

            Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
            true
        }

        val author = view.findViewById<TextView>(R.id.message_author)
        author?.text = data.from

        view.findViewById<TextView>(R.id.message_date)?.apply {
            text = data.dateFormatted()
            savedDateVisibility = visibility

            txt.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    visibility = View.VISIBLE
                    this.animate().alpha(1.0f)
                } else {
                    this.animate().alpha(0.0f).withEndAction {
                        visibility = savedDateVisibility
                    }
                }

            }
        }

    }
}