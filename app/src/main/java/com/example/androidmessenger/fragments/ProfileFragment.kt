package com.example.androidmessenger.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidmessenger.databinding.FragmentProfileBinding
import com.example.androidmessenger.saveLog.Person

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val person = arguments?.getSerializable(Person.nameForBundle) as Person
        binding.profileLogin.text = person.login
        binding.profileMail.append(" Скрыто")
        binding.profileId.append("\n${person.uid}")
        binding.profileFirstName.append(" ${person.firstName}")
        binding.profileSecondName.append(" ${person.secondName}")
        binding.profileRole.append(" ${person.role}")
        binding.profileNumPhone.append(" ${person.numPhone}")
        binding.profileAge.append(" ${person.age}")
        binding.profileAddress.append(" ${person.address}")
        binding.input.append(" Email")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}