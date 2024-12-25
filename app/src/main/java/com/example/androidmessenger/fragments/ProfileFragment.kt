package com.example.androidmessenger.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.androidmessenger.databinding.FragmentProfileBinding
import com.example.androidmessenger.saveLog.PersonJ
import org.jetbrains.annotations.ApiStatus

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
        val person = arguments?.getSerializable(PersonJ.nameForBundle) as PersonJ
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

        binding.callBTN.setOnClickListener {
            if (person.numPhone != null && person.numPhone != "") {
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + person.numPhone)))
            }else{
                Toast.makeText(
                    requireContext(),
                    "Не указан номер телефона",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.smsBTN.setOnClickListener{
            if (person.numPhone != null && person.numPhone != "") {
                startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+person.numPhone)))
            }else{
                Toast.makeText(
                    requireContext(),
                    "Не указан номер телефона",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}