package com.example.androidmessenger.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.androidmessenger.R
import com.example.androidmessenger.databinding.FragmentRegisterBinding
import com.example.androidmessenger.saveLog.Person
import com.example.androidmessenger.saveLog.RWDatadase
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.database

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.passwordSignUpET.setInputType(129)
        binding.passwordConfirmSignUpET.setInputType(129)
        auth = Firebase.auth
        binding.signInBTN.setOnClickListener {
            signUpUser()
        }
        binding.redirectLogInTV.setOnClickListener {
            view
                .findNavController()
                .navigate(
                    R.id.action_registerFragment_to_logInFragment
                )
        }
        binding.viewPasswordBTN.setOnClickListener {
            if (binding.passwordSignUpET.inputType == 129) {
                binding.passwordSignUpET.inputType = EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.passwordConfirmSignUpET.isEnabled = false
            } else {
                binding.passwordSignUpET.setInputType(129)
                binding.passwordConfirmSignUpET.isEnabled = true
            }
            binding.passwordSignUpET.setSelection(binding.passwordSignUpET.length());
        }
        binding.passwordSignUpET.setOnClickListener {
            binding.passwordSignUpET.setSelectAllOnFocus(true)
        }

        binding.passwordConfirmSignUpET.setOnClickListener {
            binding.passwordConfirmSignUpET.setSelectAllOnFocus(true)
        }
    }

    private fun signUpUser() {
        val email = binding.emailSignUpET.text.toString()
        val pass = binding.passwordSignUpET.text.toString()
        val confirmPass = binding.passwordConfirmSignUpET.text.toString()
        if (binding.passwordConfirmSignUpET.isEnabled) {
            if (email.isBlank() || pass.isBlank() || confirmPass.isBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Все поля должны быть заполнены",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            if (pass != confirmPass) {
                Toast.makeText(
                    requireContext(),
                    "Пароли не совпадают",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
        } else {
            if (email.isBlank() || pass.isBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Все поля должны быть заполнены",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
        }
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(requireActivity()) {
            if (it.isSuccessful) {
                Toast.makeText(
                    requireContext(),
                    "Успешно зарегистрирован",
                    Toast.LENGTH_SHORT
                ).show()
                val person = Person(
                    login = email
                )
                RWDatadase.sendPersonToFirebase(person)
                requireView()
                    .findNavController()
                    .navigate(
                        R.id.action_registerFragment_to_logInFragment
                    )
            } else {
                if (auth.currentUser != null) {
                    Toast.makeText(
                        requireContext(),
                        "Пользователь уже существует",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Toast.makeText(
                    requireContext(),
                    "Регистрация не прошла",
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