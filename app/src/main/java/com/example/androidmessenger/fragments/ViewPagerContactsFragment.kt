package com.example.androidmessenger.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidmessenger.R
import com.example.androidmessenger.databinding.FragmentViewPagerContactsBinding
import com.example.androidmessenger.saveLog.PersonJ
import com.example.androidmessenger.saveLog.RWDatadase
import com.example.androidmessenger.service.PersonsAdapter
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database

class ViewPagerContactsFragment : Fragment() {

    private lateinit var adapter: PersonsAdapter
    private var listLog = arrayListOf<PersonJ>()
    private var _binding: FragmentViewPagerContactsBinding? = null
    private val binding get() = _binding!!
    private var personMain: PersonJ? = null

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

        readPersonFromFirebase()

        adapter = PersonsAdapter(listLog)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        RWDatadase.readUsers(listLog, adapter)

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun readPersonFromFirebase() {
        val id = FirebaseAuth.getInstance().uid!!
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