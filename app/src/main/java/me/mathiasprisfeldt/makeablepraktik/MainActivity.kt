package me.mathiasprisfeldt.makeablepraktik

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*
import me.mathiasprisfeldt.makeablepraktik.extensions.validateNotEmpty
import me.mathiasprisfeldt.makeablepraktik.services.ChatService


class MainActivity : AppCompatActivity() {
    private var chatService = ChatService()
    private lateinit var chatRoomAdapter: ArrayAdapter<String>

    private var cachedChatRooms: List<String> = emptyList()
    set(value) {
        if (value.isEmpty()) return

        chatRoomAdapter.clear()
        chatRoomAdapter.addAll(value)
        chatRoomAdapter.notifyDataSetChanged()

        field = value
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chatRoomAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            ArrayList<String>()
        )

        username.requestFocus()
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

        chatService.chatRooms.observe(this, Observer {
            if (chatroom_selector.isPopupShowing) return@Observer

            cachedChatRooms = it ?: emptyList()
        })

        chatroom_selector.apply {
            setAdapter(this@MainActivity.chatRoomAdapter)
            threshold = 1

            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus && text.isNullOrEmpty() && error.isNullOrEmpty()) showDropDown()
            }

            setOnDismissListener {
                chatService.chatRooms.value?.let {
                    if (it == cachedChatRooms) return@setOnDismissListener

                    cachedChatRooms = it
                }
            }
        }

        home_show_chatroom_list.setOnClickListener {
            chatroom_selector.requestFocus()
            chatroom_selector.showDropDown()
        }
    }

    private fun onLogin() {
        var hasError = false
        var shouldHaveFocus: EditText?

        val chatRoomText: String = chatroom_selector.validateNotEmpty().let {
            shouldHaveFocus = chatroom_selector
            hasError = !it.second || hasError
            it.first
        }

        val usernameTxt: String = username.validateNotEmpty("Username cant be empty").let {
            shouldHaveFocus = username
            hasError = !it.second || hasError
            it.first
        }

        if (hasError) {
            shouldHaveFocus?.requestFocus()
            return
        }

        val intent = Intent(this, ChatActivity::class.java).apply {
            putExtra(UsernameExtra, usernameTxt)
            putExtra(ChatRoomExtra, chatRoomText)
        }

        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    override fun onResume() {
        super.onResume()

        if (username.text.toString().isNotEmpty()) {
            chatroom_selector?.apply {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
            }
        }
    }

    companion object {
        var UsernameExtra: String = "USERNAME"
        var ChatRoomExtra: String = "CHATROOM"
    }
}
