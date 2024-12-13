package com.example.androidmessenger

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.androidmessenger.databinding.FragmentLogInBinding
import com.google.firebase.auth.FirebaseAuth

class LogInFragment : Fragment() {
    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.passwordLogInET.setInputType(129)
        auth = FirebaseAuth.getInstance()
        binding.registerTV.setOnClickListener {
            view.findNavController().navigate(R.id.action_logInFragment_to_registerFragment)
        }
        binding.logInBTN.setOnClickListener {
            logIn()
        }
        binding.viewPasswordBTN.setOnClickListener {
            if (binding.passwordLogInET.inputType == 129) {
                binding.passwordLogInET.inputType = EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }else{
                binding.passwordLogInET.setInputType(129)
//                binding.passwordLogInET.inputType = EditorInfo.TYPE_TEXT_VARIATION_PASSWORD
            }
            binding.passwordLogInET.setSelection(binding.passwordLogInET.length());
        }
    }

    private fun logIn() {
        val email = binding.emailLogInET.text.toString()
        val pass = binding.passwordLogInET.text.toString()
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(requireActivity()) {
                if (it.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        "Успешно вошёл в систему",
                        Toast.LENGTH_SHORT
                    ).show()
                    val bundle = Bundle()
                    bundle.putString("email", email)
                    view
                        ?.findNavController()
                        ?.navigate(R.id.action_logInFragment_to_menuFragment, bundle)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Не удалось войти в систему",
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