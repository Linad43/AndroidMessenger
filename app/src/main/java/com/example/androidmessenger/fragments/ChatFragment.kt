package com.example.androidmessenger.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidmessenger.chatAdapter.ChatRecycleAdapter
import com.example.androidmessenger.chatAdapter.ElementChat
import com.example.androidmessenger.chatAdapter.GetMessage
import com.example.androidmessenger.chatAdapter.SendMessage
import com.example.androidmessenger.databinding.FragmentChatBinding
import com.example.androidmessenger.saveLog.Person
import com.example.androidmessenger.R

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

        val personChat = arguments?.getSerializable(Person.nameForBundle) as Person
        adapter = ChatRecycleAdapter(listChat)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.titleLoginTV.text = personChat.login
        binding.cardView.setOnClickListener{
            //TODO
        }
        binding.imagePersonIV.setOnClickListener{
            val bundle = Bundle()
            bundle.putSerializable(Person.nameForBundle, personChat)
            findNavController().navigate(R.id.action_chatFragment_to_profileFragment, bundle)
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}