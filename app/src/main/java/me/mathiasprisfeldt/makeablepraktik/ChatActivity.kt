package me.mathiasprisfeldt.makeablepraktik

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.activity_chat.*
import me.mathiasprisfeldt.makeablepraktik.recycler_views.MessagesRecyclerAdapter
import me.mathiasprisfeldt.makeablepraktik.services.ChatService
import me.mathiasprisfeldt.makeablepraktik.types.Message


class ChatActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var chatService: ChatService
    private lateinit var username: String
    private lateinit var chatRoom: String
    private lateinit var msgRecyclerAdapter: MessagesRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // If both username and chat room is empty something is wrong and return to main activity.
        intent?.let {
            username = it.getStringExtra(MainActivity.UsernameExtra)
            chatRoom = it.getStringExtra(MainActivity.ChatRoomExtra)

            if (username.isEmpty() || chatRoom.isEmpty()) {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

        chatService = ChatService(username, chatRoom)

        supportActionBar?.title = chatRoom
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Send msg when pressing send button or action button in keyboard
        send.setOnClickListener(this)

        // If the msg text field loses focus, hide the keyboard
        msg.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
            }
        }

        msg.setOnEditorActionListener { textView, _, _ ->
            onClick(textView)
            true
        }

        val layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
            isItemPrefetchEnabled = true
        }

        messages.layoutManager = layoutManager

        val recyclerOptions = FirestoreRecyclerOptions.Builder<Message>().setQuery(chatService.messages, Message::class.java).build()
        msgRecyclerAdapter = MessagesRecyclerAdapter(recyclerOptions, messages, layoutManager, chatService, baseContext)
        messages.adapter = msgRecyclerAdapter
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)

        if (newConfig?.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
            messages.scrollToPosition(messages.childCount - 1)
        } else if (newConfig?.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            messages.scrollToPosition(messages.childCount - 1)
        }
    }

    override fun onStart() {
        super.onStart()
        msgRecyclerAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        msgRecyclerAdapter.stopListening()
    }

    override fun onClick(p0: View?) {
        val text = msg.text.toString()
        if (text.isBlank()) return

        chatService.send(text)
        msg.text.clear()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
