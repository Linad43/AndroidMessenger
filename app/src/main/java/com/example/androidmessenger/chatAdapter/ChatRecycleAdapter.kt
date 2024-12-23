package com.example.androidmessenger.chatAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmessenger.R


class ChatRecycleAdapter(
    val messages: ArrayList<ElementChat>,
) : RecyclerView.Adapter<ChatRecycleAdapter.ChatViewHolder>() {
    private var onClickListener: OnClickListener? = null

    interface OnClickListener {
        fun onClick(messages: ArrayList<ElementChat>, position: Int)
    }

    class ChatViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        val cardGetMessage = itemView.findViewById<CardView>(R.id.cardGetMessage)
        val cardSendMessage = itemView.findViewById<CardView>(R.id.cardSendMessage)
        val getMessage = itemView.findViewById<TextView>(R.id.getMessage)
        val sendMessage = itemView.findViewById<TextView>(R.id.sendMessage)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ChatViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_chats, parent, false)
        return ChatViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ChatViewHolder,
        position: Int,
    ) {
        val message = messages[position]
        if (message::class == GetMessage::class){
            holder.cardSendMessage.visibility = View.INVISIBLE
            holder.cardGetMessage.visibility = View.VISIBLE
            holder.getMessage.text = message.textChat
        }else{
            holder.cardGetMessage.visibility = View.INVISIBLE
            holder.cardSendMessage.visibility = View.VISIBLE
            holder.sendMessage.text = message.textChat
        }
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(messages, position)
            }
        }
    }

//    override fun onBindViewHolder(
//        holder: ChatViewHolder,
//        position: Int,
//    ) {
//        val message = messages[position]
//        if (message.getMessage != null) {
//            holder.cardGetMessage.visibility = View.VISIBLE
//            holder.getMessage.text = message.getMessage
//        } else {
//            holder.cardSendMessage.visibility = View.VISIBLE
//            holder.sendMessage.text = message.sendMessage
//        }
//        holder.itemView.setOnClickListener {
//            if (onClickListener != null) {
//                onClickListener!!.onClick(messages, position)
//            }
//        }
//    }

    override fun getItemCount() = messages.size
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
}