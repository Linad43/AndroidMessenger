package com.example.androidmessenger.saveLog

import android.annotation.SuppressLint
import com.example.androidmessenger.chatAdapter.ChatMessage
import com.example.androidmessenger.chatAdapter.ChatRecycleAdapter
import com.example.androidmessenger.service.PersonsAdapter
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database

class RWDatadase {
    companion object {
        @SuppressLint("NotifyDataSetChanged")
        fun readUsers(listLog: ArrayList<PersonJ>, adapter: PersonsAdapter) {
            Firebase.database.reference.child("users")
                .get().addOnSuccessListener {
                    for (element in it.children) {
                        val user = element.getValue(PersonJ::class.java)!!
                        if (user.uid == FirebaseAuth.getInstance().uid) {
                            continue
                        }
                        listLog.add(user)
                    }
                    adapter.notifyDataSetChanged()
                }
        }

        @SuppressLint("NotifyDataSetChanged")
        fun fillListChat(
            personMain: PersonJ,
            personReceiver: PersonJ,
            adapter: ChatRecycleAdapter,
            listChat: ArrayList<ChatMessage>,
        ) {
            Firebase.database.reference
                .child("messages")
                .child(personMain.uid)
                .child(personReceiver.uid)
                .get().addOnSuccessListener {
                    listChat.clear()
                    for (element in it.children) {
                        val elementChat =
                            element.getValue(ChatMessage::class.java) as ChatMessage
                        listChat.add(elementChat)
                        listChat.sortedBy { it.messageTime }
                    }
                    adapter.notifyDataSetChanged()
                }
        }

        fun sendPersonToFirebase(person: PersonJ) {
            val database = Firebase.database.reference
                .child("users")
            val map: HashMap<String, PersonJ> = HashMap()
            map[person.uid.toString()] = person
            database.updateChildren(map as Map<String, Any>)
        }
    }
}