package com.example.pinsurance.chatbot

sealed class ChatItem {
    data class SenderMessage(val text: String): ChatItem()
    data class ReceiverMessage(val text: String): ChatItem()
    data class ChipRecyclerView(val chipList: List<Chip>): ChatItem()
}