package com.example.androidmessenger.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidmessenger.R
import com.example.androidmessenger.chatAdapter.ChatMessage
import com.example.androidmessenger.chatAdapter.ChatRecycleAdapter
import com.example.androidmessenger.databinding.FragmentChatBinding
import com.example.androidmessenger.saveLog.PersonJ
import com.example.androidmessenger.saveLog.RWDatadase
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val listChat = ArrayList<ChatMessage>()
    private lateinit var adapter: ChatRecycleAdapter
    private lateinit var personMain: PersonJ
    private lateinit var personReceiver: PersonJ

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listChat.clear()
        personMain = arguments?.getSerializable("personMain") as PersonJ
        personReceiver = arguments?.getSerializable("personReceiver") as PersonJ
        adapter = ChatRecycleAdapter(listChat)

        RWDatadase.fillListChat(
            personMain,
            personReceiver,
            adapter,
            listChat
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.titleLoginTV.text = personReceiver.login
        binding.cardView.setOnClickListener {
            //TODO images
        }
        binding.imagePersonIV.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(PersonJ.nameForBundle, personReceiver)
            findNavController().navigate(R.id.action_chatFragment_to_profileFragment, bundle)
        }
        binding.imageSend.setOnClickListener {
            sendMessage()
        }
        adapter.setOnClickListener(object :
            ChatRecycleAdapter.OnClickListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onClick(messages: ArrayList<ChatMessage>, position: Int) {
                val dialog = AlertDialog.Builder(requireContext())
                dialog.setTitle("Удаление")
                dialog.setMessage("Удалить это сообщение?")
                dialog.setPositiveButton("Да") { _, _ ->
                    var key: DataSnapshot? = null
                    val elementListChat = listChat[position]
                    Firebase.database.reference
                        .child("messages")
                        .child(personMain.uid)
                        .child(personReceiver.uid)
                        .get().addOnSuccessListener {
                            for (element in it.children) {
                                val elementChat =
                                    element.getValue(ChatMessage::class.java) as ChatMessage
                                if (elementChat.messageTime == elementListChat.messageTime &&
                                    elementChat.messageUser == elementListChat.messageUser &&
                                    elementChat.messageText == elementListChat.messageText
                                ) {
                                    key = element
                                }
                            }
                            key?.ref?.removeValue()
                        }
                    Firebase.database.reference
                        .child("messages")
                        .child(personReceiver.uid)
                        .child(personMain.uid)
                        .get().addOnSuccessListener {
                            for (element in it.children) {
                                val elementChat =
                                    element.getValue(ChatMessage::class.java) as ChatMessage
                                if (elementChat.messageTime == elementListChat.messageTime &&
                                    elementChat.messageUser == elementListChat.messageUser &&
                                    elementChat.messageText == elementListChat.messageText
                                ) {
                                    key = element
                                }
                            }
                            key?.ref?.removeValue()
                        }
//                    listChat.remove(elementListChat)
//                    adapter.notifyDataSetChanged()
                    RWDatadase.fillListChat(
                        personMain,
                        personReceiver,
                        adapter,
                        listChat
                    )
//                    listChat.remove(listChat[position])

                }
                dialog.setNegativeButton("Нет") { _, _ ->
                }
                dialog.create().show()
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sendMessage() {
        val message = binding.sendMessageET.text.toString()
        val chatMessage = ChatMessage(message, personMain.uid)
        FirebaseDatabase.getInstance()
            .getReference()
            .child("messages")
            .child(personMain.uid)
            .child(personReceiver.uid)
            .push()
            .setValue(
                chatMessage
            )
        FirebaseDatabase.getInstance()
            .getReference()
            .child("messages")
            .child(personReceiver.uid)
            .child(personMain.uid)
            .push()
            .setValue(
                chatMessage
            )
        RWDatadase.fillListChat(
            personMain,
            personReceiver,
            adapter,
            listChat
        )
        binding.sendMessageET.text.clear()
    }
}

