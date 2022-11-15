package com.example.pinsurance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem

class ChatRoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        /** adding back button */
        val display = supportActionBar
        display?.setDisplayHomeAsUpEnabled(true)
    }

    /** clicking back button */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else ->super.onOptionsItemSelected(item)
        }
    }
}