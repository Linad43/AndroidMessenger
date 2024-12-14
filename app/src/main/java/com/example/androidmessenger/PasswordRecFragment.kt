package com.example.androidmessenger

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.androidmessenger.databinding.FragmentLogInBinding
import com.example.androidmessenger.databinding.FragmentPasswordRecBinding
import com.google.android.play.core.integrity.au
import com.google.firebase.auth.FirebaseAuth

class PasswordRecFragment : Fragment() {
    private var _binding: FragmentPasswordRecBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentPasswordRecBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding.sendBTN.setOnClickListener{
            recoverPassword()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun recoverPassword() {
        if (binding.emailLogInET.text.isEmpty()){
            Toast.makeText(
                requireContext(),
                "Поле не может быть пустым",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        val email = binding.emailLogInET.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(
                requireContext(),
                "Не корректный Email...",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
//        Toast.makeText(
//            requireContext(),
//            "Отправлено сообщение на введеный Email...",
//            Toast.LENGTH_SHORT
//        ).show()
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                Toast.makeText(
                    requireContext(),
                    "Инструкции отправлены на ваш email",
                    Toast.LENGTH_SHORT
                ).show()
                fragmentManager?.popBackStack()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    requireContext(),
                    "${e.message}",
                    Toast.LENGTH_SHORT)
                    .show()
            }
    }
}