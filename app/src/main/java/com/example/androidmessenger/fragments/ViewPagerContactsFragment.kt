package com.example.androidmessenger.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidmessenger.R
import com.example.androidmessenger.databinding.FragmentViewPagerContactsBinding
import com.example.androidmessenger.saveLog.Person
import com.example.androidmessenger.saveLog.RWDatadase
import com.example.androidmessenger.service.PersonsAdapter
import com.google.firebase.Firebase
import com.google.firebase.database.database

class ViewPagerContactsFragment : Fragment() {

    private lateinit var adapter: PersonsAdapter
    private var listLog = arrayListOf<Person>()
    private var _binding: FragmentViewPagerContactsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentViewPagerContactsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listLog.clear()
        //listLog = readUsers()

        adapter = PersonsAdapter(listLog)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        RWDatadase.readUsers(listLog, adapter)

        adapter.setOnPersonClickListener(object :
            PersonsAdapter.OnPersonsClickListener {
            override fun onPersonClick(persons: Person, position: Int) {
                findNavController().navigate(R.id.action_menuFragment_to_chatFragment)
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
//    @SuppressLint("NotifyDataSetChanged")
//    private fun readUsers() {
//        Firebase.database.reference.child("users")
//            .get().addOnSuccessListener {
//                for (element in it.children) {
//                    val user = element.getValue(Person::class.java)!!
//                    listLog.add(user)
//                }
//                adapter.notifyDataSetChanged()
//            }
//    }
}