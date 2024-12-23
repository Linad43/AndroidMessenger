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
import com.example.androidmessenger.chatAdapter.ChatRecycleAdapter
import com.example.androidmessenger.chatAdapter.ElementChat
import com.example.androidmessenger.chatAdapter.GetMessage
import com.example.androidmessenger.chatAdapter.Removable
import com.example.androidmessenger.chatAdapter.SendMessage
import com.example.androidmessenger.databinding.FragmentChatBinding
import com.example.androidmessenger.saveLog.Person
import com.google.firebase.Firebase
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val listChat = ArrayList<ElementChat>()
    private lateinit var adapter: ChatRecycleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listChat.add(GetMessage("one"))
        listChat.add(SendMessage("two"))
        listChat.add(GetMessage("three"))
        val personChat = arguments?.getSerializable(Person.nameForBundle) as Person
        adapter = ChatRecycleAdapter(listChat)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.titleLoginTV.text = personChat.login
        binding.cardView.setOnClickListener {
            //TODO images
        }
        binding.imagePersonIV.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(Person.nameForBundle, personChat)
            findNavController().navigate(R.id.action_chatFragment_to_profileFragment, bundle)
        }
        binding.imageSend.setOnClickListener{
            sendMessage()
        }
        adapter.setOnClickListener(object :
            ChatRecycleAdapter.OnClickListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onClick(messages: ArrayList<ElementChat>, position: Int) {
                val dialog = AlertDialog.Builder(requireContext())
                dialog.setTitle("Удаление")
                dialog.setMessage("Удалить это сообщение?")
                dialog.setPositiveButton("Да") { _, _ ->
                    listChat.remove(listChat[position])
                    adapter.notifyDataSetChanged()
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

//    override fun remove(element: ElementChat) {
//        listChat.remove(element)
//    }

    private fun sendMessage() {
    }
}

