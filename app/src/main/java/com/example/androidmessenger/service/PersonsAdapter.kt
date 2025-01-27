package com.example.androidmessenger.service

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmessenger.R
import com.example.androidmessenger.saveLog.Person
import com.example.androidmessenger.saveLog.PersonJ

class PersonsAdapter(
    private val persons: ArrayList<PersonJ>,
) : RecyclerView.Adapter<PersonsAdapter.PersonsViewHolder>() {
    private var onPersonsClickListener: OnPersonsClickListener? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PersonsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_contacts_and_chats, parent, false)
        return PersonsViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: PersonsViewHolder,
        position: Int,
    ) {
        val person = persons[position]
        holder.imageIV.setImageResource(R.drawable.person_50)
        holder.loginTV.text = person.login
        holder.itemView.setOnClickListener {
            if (onPersonsClickListener != null) {
                onPersonsClickListener!!.onPersonClick(person, position)
            }
        }
    }

    override fun getItemCount(): Int = persons.size

    interface OnPersonsClickListener {
        fun onPersonClick(persons: PersonJ, position: Int)
    }

    class PersonsViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        val imageIV = itemView.findViewById<ImageView>(R.id.imageIV)
        val loginTV = itemView.findViewById<TextView>(R.id.loginTV)
    }

    fun setOnPersonClickListener(onPersonsClickListener: OnPersonsClickListener) {
        this.onPersonsClickListener = onPersonsClickListener
    }
}