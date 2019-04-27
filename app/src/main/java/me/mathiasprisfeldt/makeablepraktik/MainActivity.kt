package me.mathiasprisfeldt.makeablepraktik

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import me.mathiasprisfeldt.makeablepraktik.extensions.Validate
import me.mathiasprisfeldt.makeablepraktik.services.ChatService


class MainActivity : AppCompatActivity() {
    private var chatService = ChatService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Login"

        username.setOnEditorActionListener { v, actionId, event ->

            false
        }

        chatroom_selector.setOnEditorActionListener { _, _, _ ->
            onLogin()
            true
        }

        home_login_btn.setOnClickListener {
            onLogin()
        }

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            ArrayList<String>()
        )

        chatService.chatRooms.observe(this, Observer {
            it?.run {
                adapter.clear()
                adapter.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })

        chatroom_selector.apply {
            setAdapter(adapter)
            threshold = 0

            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) showDropDown()
            }
        }
    }

    private fun onLogin() {
        var hasError = false

        val usernameTxt: String = username.Validate().let {
            hasError = !it.second || hasError
            it.first
        }

        val chatRoomText: String = chatroom_selector.Validate().let {
            hasError = !it.second || hasError
            it.first
        }

        if (hasError) return

        val intent = Intent(this, ChatActivity::class.java).apply {
            putExtra(UsernameExtra, usernameTxt)
            putExtra(ChatRoomExtra, chatRoomText)
        }

        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

        username?.apply {
            text.clear()
            requestFocus()

            // Forcefully open the keyboard
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }

        chatroom_selector.text.clear()
    }

    companion object {
        var UsernameExtra: String = "USERNAME"
        var ChatRoomExtra: String = "CHATROOM"
    }
}
