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
import com.example.androidmessenger.databinding.FragmentViewPagerChatsBinding
import com.example.androidmessenger.saveLog.PersonJ
import com.example.androidmessenger.saveLog.RWDatadase
import com.example.androidmessenger.service.PersonsAdapter
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database

class ViewPagerChatsFragment : Fragment() {

    private lateinit var adapter: PersonsAdapter
    private var listLog = arrayListOf<PersonJ>()
    private var _binding: FragmentViewPagerChatsBinding? = null
    private val binding get() = _binding!!
    private var personMain: PersonJ? = null
    private val listPersonChats = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentViewPagerChatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listLog.clear()

        readPersonFromFirebase()

        adapter = PersonsAdapter(listLog)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        adapter.setOnPersonClickListener(object :
            PersonsAdapter.OnPersonsClickListener {
            override fun onPersonClick(personReceiver: PersonJ, position: Int) {
                if (personMain != null) {
                    val bundle = Bundle()
                    bundle.putSerializable("personReceiver", personReceiver)
                    bundle.putSerializable("personMain", personMain!!)
                    findNavController().navigate(R.id.action_menuFragment_to_chatFragment, bundle)
                }
            }
        })
        binding.searchBTN.setOnClickListener {
            val textSearch = binding.searchET.text
            val newList = arrayListOf<PersonJ>()
            listLog.forEach {
                newList.add(it)
            }
            listLog.clear()
            if (textSearch != null && textSearch.toString() != "") {
                listLog.forEach {
                    newList.add(it)
                }
                listLog.clear()
                listLog.addAll(newList.filter {
                    it.login.contains(textSearch, ignoreCase = true)
                })
                adapter.notifyDataSetChanged()
            } else {
                RWDatadase.readUsers(listLog, adapter)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun readPersonFromFirebase() {
        val id = FirebaseAuth.getInstance().uid!!
        Firebase.database.reference
            .child("messages")
            .child(id)
            .get().addOnSuccessListener {
                for (element in it.children) {
                    listPersonChats.add(element.key.toString())
                }
            }
        Firebase.database.reference
            .child("users")
            .get().addOnSuccessListener {
                for (element in it.children) {
                    val person = element.getValue(PersonJ::class.java) as PersonJ
                    if (listPersonChats.contains(person.uid)) {
                        listLog.add(person)
                    }
                }
                listLog.sortedBy { it.login }
                adapter.notifyDataSetChanged()
            }
        Firebase.database.reference
            .child("users")
            .child(id)
            .get().addOnSuccessListener {
                if (it.getValue(PersonJ::class.java) != null) {
                    val person = it.getValue(PersonJ::class.java)!!
                    personMain = person
                }
            }
    }
}