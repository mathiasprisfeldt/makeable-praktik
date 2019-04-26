package me.mathiasprisfeldt.makeablepraktik

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var chatService = ChatService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Login"

        username.setOnEditorActionListener { _, _, _ ->
            onLogin()
            true
        }

        home_login_btn.setOnClickListener {
            onLogin()
        }

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            chatService.chatRooms
        )

        chatroom_selector.apply {
            setAdapter(adapter)
            threshold = 0

            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) showDropDown()
            }
        }
    }

    private fun onLogin() {
        val intent = Intent(this, ChatActivity::class.java).apply {
            putExtra(UsernameExtra, username.text.toString())
            putExtra(ChatRoomExtra, chatroom_selector.text.toString())
        }

        username.text.clear()

        startActivity(intent)
    }

    companion object {
        var UsernameExtra: String = "USERNAME"
        var ChatRoomExtra: String = "CHATROOM"
    }
}
