package com.example.pinsurance.handin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pinsurance.R

class PersonAdapter(private val personList:ArrayList<Person>)
    : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    var OnItemClick : ((Person) -> Unit)? = null

    class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView : TextView = itemView.findViewById(R.id.in_per_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_person , parent , false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = personList[position]
        holder.textView.text = person.name

        holder.itemView.setOnClickListener {
            OnItemClick?.invoke(person)
        }
    }

    override fun getItemCount(): Int {
        return personList.size
    }

}