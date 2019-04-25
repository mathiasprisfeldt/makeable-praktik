package me.mathiasprisfeldt.makeablepraktik

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Login"

        username.setOnEditorActionListener { textView, i, keyEvent ->
            onLogin()
            true
        }

        home_login_btn.setOnClickListener {
            onLogin()
        }
    }

    private fun onLogin() {
        val intent = Intent(this, ChatActivity::class.java).apply {
            putExtra(UsernameExtra, username.text.toString())
        }

        username.text.clear()

        startActivity(intent)
    }

    companion object {
        var UsernameExtra: String = "USERNAME"
    }
}
