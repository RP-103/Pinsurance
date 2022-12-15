package com.example.pinsurance.chatbot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pinsurance.databinding.ChatChipItemBinding

class ChipAdapter(var chipList: List<Chip>) : RecyclerView.Adapter<ChipAdapter.ViewHolder>() {

    var chipClickListener: ((view: View, chip: Chip, position: Int) -> Unit)? = null

    inner class ViewHolder(val binding: ChatChipItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ChatChipItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chip: Chip = chipList[position]
        holder.binding.button.text = chip.text

        holder.binding.button.setOnClickListener {
            chipClickListener?.invoke(it, chip, position)
        }
    }

    override fun getItemCount(): Int {
        return chipList.size
    }
}