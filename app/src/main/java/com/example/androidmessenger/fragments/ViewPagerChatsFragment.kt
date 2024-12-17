package com.example.androidmessenger.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidmessenger.R
import com.example.androidmessenger.databinding.FragmentViewPagerChatsBinding
import com.example.androidmessenger.service.PersonsAdapter

class ViewPagerChatsFragment : Fragment() {

    private lateinit var adapter: PersonsAdapter
    private var listLog = arrayListOf<String>()
    private var _binding: FragmentViewPagerChatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentViewPagerChatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PersonsAdapter(listLog)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        adapter.setOnPersonClickListener(object :
            PersonsAdapter.OnPersonsClickListener {
            override fun onPersonClick(persons: String, position: Int) {
                findNavController().navigate(R.id.action_menuFragment_to_chatFragment)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}