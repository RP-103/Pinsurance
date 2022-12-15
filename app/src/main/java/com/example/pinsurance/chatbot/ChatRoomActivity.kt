package com.example.pinsurance.chatbot

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pinsurance.R
import com.example.pinsurance.databinding.ActivityChatRoomBinding
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.auth.oauth2.ServiceAccountCredentials
import com.google.cloud.dialogflow.v2.*
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList


class ChatRoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatRoomBinding
    private var messageList: ArrayList<Message> = ArrayList()

    private var itemList: ArrayList<ChatItem> = ArrayList()
    private lateinit var chatAdapter: ChatAdapter

    //dialogFlow
    private var sessionsClient: SessionsClient? = null
    private var sessionName: SessionName? = null
    private val uuid = UUID.randomUUID().toString()

    // payload custom
    var chipList: List<Chip> = ArrayList()


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** adding back button */
        val display = supportActionBar
        display?.setDisplayHomeAsUpEnabled(true)

        //setting adapter to recyclerview
        chatAdapter = ChatAdapter(itemList)
        binding.chatView.adapter = chatAdapter

        // lambda
        chatAdapter.itemClickListener = { view, item, position ->
            val message = when (item) {
                is ChatItem.ChipRecyclerView -> "ChipRV Clicked"
                is ChatItem.ReceiverMessage -> "ReceiverMessage Clicked"
                is ChatItem.SenderMessage -> "SenderMessage Clicked"
            }
            if (item is ChatItem.ChipRecyclerView) {
                val chip = chipList[position]
                binding.inputMessage.setText(chip.text)
            }

        }

        //onclick listener to update the list and call dialogflow
        binding.btnSend.setOnClickListener {
            val message: String = binding.inputMessage.text.toString()
            if (message.isNotEmpty()) {
                addMessageToList(message, false)
                sendMessageToBot(message)
            } else {
                Toast.makeText(this@ChatRoomActivity, "Please enter text!", Toast.LENGTH_SHORT).show()
            }
        }

        //initialize bot config
        setUpBot()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addMessageToList(message: String, isReceived: Boolean) {
        if (isReceived) {
            itemList.add(ChatItem.ReceiverMessage(message))
        }
        else {
            itemList.add(ChatItem.SenderMessage(message))
        }


        binding.inputMessage.setText("")
        chatAdapter.notifyDataSetChanged()
        binding.chatView.layoutManager?.scrollToPosition(itemList.size - 1)
    }

    private  fun addChipToList(chipList: List<Chip>) {

        itemList.add(ChatItem.ChipRecyclerView(chipList))
        chatAdapter.notifyDataSetChanged()
        binding.chatView.layoutManager?.scrollToPosition(itemList.size - 1)
    }

    private fun setUpBot() {
        try {
            val stream = this.resources.openRawResource(R.raw.credential)
            val credentials: GoogleCredentials = GoogleCredentials.fromStream(stream)
                .createScoped("https://www.googleapis.com/auth/cloud-platform")
            val projectId: String = (credentials as ServiceAccountCredentials).projectId
            val settingsBuilder: SessionsSettings.Builder = SessionsSettings.newBuilder()
            val sessionsSettings: SessionsSettings = settingsBuilder.setCredentialsProvider(
                FixedCredentialsProvider.create(credentials)
            ).build()
            sessionsClient = SessionsClient.create(sessionsSettings)
            sessionName = SessionName.of(projectId, uuid)
            Log.d("TAG", "projectId : $projectId")
        } catch (e: Exception) {
            Log.d("TAG", "setUpBot: " + e.message)
        }
    }

    private fun sendMessageToBot(message: String) {
        val input = QueryInput.newBuilder()
            .setText(TextInput.newBuilder().setText(message).setLanguageCode("en-US")).build()

        GlobalScope.launch {
            sendMessageInBg(input)
        }
    }

    private suspend fun sendMessageInBg(
        queryInput: QueryInput
    ) {
        withContext(Default) {
            try {
                val detectIntentRequest = DetectIntentRequest.newBuilder()
                    .setSession(sessionName.toString())
                    .setQueryInput(queryInput)
                    .build()
                val result = sessionsClient?.detectIntent(detectIntentRequest)
                if (result != null) {
                    runOnUiThread {
                        updateUI(result)
                    }
                }
            } catch (e: java.lang.Exception) {
                Log.d("Error", "doInBackground: " + e.message)
                e.printStackTrace()
            }
        }
    }

    private fun updateUI(response: DetectIntentResponse) {

        val count: Int = response.queryResult.fulfillmentMessagesCount

        if (count > 0) {
            val messageList: List<Intent.Message> = response.queryResult.fulfillmentMessagesList
            messageList.forEach { e ->
                if (e.hasPayload()) {
                    // string list

                    Log.d("PPPPP payload fields count", e.payload.fieldsCount.toString())
                    e.payload.fieldsMap.forEach { (key, value) ->
                        Log.d("ppp key", key.toString())
                        Log.d("ppp value", value.toString())
                    }

                    // update string list to buttons

                    chipList = listOf(
                        Chip("One", false),
                        Chip("Two", false),
                        Chip("Three", false),
                        Chip("Four", false),
                    )
                    addChipToList(chipList)
                }
                else if (e.hasText()) {
                    val stringList: List<String> = e.text.textList
                    stringList.forEach {
                        Log.d("pppppp", it)
                        addMessageToList(it, true)
                    }
                }
            }
        }
        else {
            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
        }
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