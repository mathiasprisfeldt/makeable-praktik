package me.mathiasprisfeldt.makeablepraktik

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.activity_chat.*
import me.mathiasprisfeldt.makeablepraktik.recycler_views.MessagesRecyclerAdapter
import android.support.v7.widget.RecyclerView



class ChatActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var msgService: MessageService
    private lateinit var username: String
    private lateinit var msgRecyclerAdapter: MessagesRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        username = intent?.getStringExtra(MainActivity.UsernameExtra) ?: return run {
            // If username is null return to login screen
            startActivity(Intent(this, MainActivity::class.java))
        }

        msgService = MessageService(username)

        supportActionBar?.title = "Chatting as $username"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Send msg when pressing send button or action button in keyboard
        send.setOnClickListener(this)
        msg.setOnEditorActionListener { textView, _, _ ->
            onClick(textView)
            true
        }

        val layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }
        messages.layoutManager = layoutManager

        val recyclerOptions = FirestoreRecyclerOptions.Builder<Message>().setQuery(msgService.messages, Message::class.java).build()
        msgRecyclerAdapter = MessagesRecyclerAdapter(recyclerOptions, messages, layoutManager)
        messages.adapter = msgRecyclerAdapter
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
        msgService.send(msg.text.toString())
        msg.text.clear()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
