package com.example.pinsurance.chatbot

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pinsurance.R
import com.example.pinsurance.databinding.ChatChipRecyclerviewBinding
import com.example.pinsurance.databinding.ChatReceiverMessageBinding
import com.example.pinsurance.databinding.ChatSenderMessageBinding
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class ChatAdapter(private var itemList: List<ChatItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var itemClickListener: ((view: View, item: ChatItem, position: Int) -> Unit)? = null

    inner class SenderViewHolder(private var binding: ChatSenderMessageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChatItem.SenderMessage) {
            binding.messageSend.text = item.text

            binding.messageSend.setOnClickListener {
                itemClickListener?.invoke(it, item, adapterPosition)
            }
        }
    }

    inner class ReceiverViewHolder(private var binding: ChatReceiverMessageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChatItem.ReceiverMessage) {
            binding.messageReceived.text = item.text

            binding.messageReceived.setOnClickListener {
                itemClickListener?.invoke(it, item, adapterPosition)
            }
        }
    }

    inner class ChipViewHolder(private val ctx: android.content.Context, private val binding: ChatChipRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChatItem.ChipRecyclerView) {
            val layoutManager: RecyclerView.LayoutManager = FlexboxLayoutManager(ctx, FlexDirection.ROW, FlexWrap.WRAP)
            binding.chipRv.layoutManager = layoutManager
            val rvAdapter = ChipAdapter(item.chipList)
            binding.chipRv.adapter = rvAdapter

            rvAdapter.chipClickListener = { view, chip, position ->
                itemClickListener?.invoke(view, item, position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        return when(itemList[position]) {
            is ChatItem.SenderMessage -> R.layout.chat_sender_message
            is ChatItem.ReceiverMessage -> R.layout.chat_receiver_message
            is ChatItem.ChipRecyclerView -> R.layout.chat_chip_recyclerview
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            R.layout.chat_sender_message -> SenderViewHolder(ChatSenderMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            R.layout.chat_receiver_message -> ReceiverViewHolder(ChatReceiverMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            R.layout.chat_chip_recyclerview -> ChipViewHolder(parent.context, ChatChipRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw IllegalArgumentException("Invalid ViewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (val item = itemList[position]) {
            is ChatItem.SenderMessage -> (holder as SenderViewHolder).bind(item)
            is ChatItem.ReceiverMessage -> (holder as ReceiverViewHolder).bind(item)
            is ChatItem.ChipRecyclerView -> (holder as ChipViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }






}