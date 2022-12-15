package com.example.pinsurance.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pinsurance.databinding.ItemSlideContainerBinding

class IntroSliderAdapter(private val introSlides: List<IntroSlide>)
    : RecyclerView.Adapter<IntroSliderAdapter.ViewHolder>() {

    // Create an inner class with name ViewHolder
    inner class ViewHolder(private val binding: ItemSlideContainerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(introSlide: IntroSlide) {
            binding.textQ1.text = introSlide.faqA
            binding.textQ2.text = introSlide.faqB
            binding.textQ3.text = introSlide.faqC
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSlideContainerBinding.inflate(LayoutInflater.from(parent.context),
                                                           parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(introSlides[position])
    }

    override fun getItemCount(): Int {
        return introSlides.size
    }


}