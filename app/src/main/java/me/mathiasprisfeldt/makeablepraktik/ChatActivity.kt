package me.mathiasprisfeldt.makeablepraktik

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var msgService: MessageService
    private lateinit var username: String

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

        send.setOnClickListener(this)
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
