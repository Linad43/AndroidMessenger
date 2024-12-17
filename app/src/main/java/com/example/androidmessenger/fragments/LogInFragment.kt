package com.example.androidmessenger.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.androidmessenger.R
import com.example.androidmessenger.saveLog.MyPreference
import com.example.androidmessenger.databinding.FragmentLogInBinding
import com.google.firebase.auth.FirebaseAuth


class LogInFragment : Fragment() {

    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

//    private lateinit var sharedPref: SharedPreferences
    private lateinit var sharedPref: MyPreference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        sharedPref = MyPreference(requireContext())
        val log = sharedPref.getLogin()
        val pass = sharedPref.getPassword()
        if (log != null && pass != null) {
            logIn(log, pass)
        }

        binding.passwordLogInET.setInputType(129)

        binding.registerTV.setOnClickListener {
            view.findNavController().navigate(R.id.action_logInFragment_to_registerFragment)
        }
        binding.logInBTN.setOnClickListener {
            val email = binding.emailLogInET.text.toString()
            val pass = binding.passwordLogInET.text.toString()
            logIn(email, pass)
        }
        binding.viewPasswordBTN.setOnClickListener {
            if (binding.passwordLogInET.inputType == 129) {
                binding.passwordLogInET.inputType = EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                binding.passwordLogInET.setInputType(129)
//                binding.passwordLogInET.inputType = EditorInfo.TYPE_TEXT_VARIATION_PASSWORD
            }
            binding.passwordLogInET.setSelection(binding.passwordLogInET.length());
        }
        binding.lostPasswordTV.setOnClickListener {
            view.findNavController().navigate(R.id.action_logInFragment_to_passwordRecFragment)
        }
    }

    @SuppressLint("CommitPrefEdits")
    private fun logIn(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(requireActivity()) {
                if (it.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        "Успешно вошёл в систему",
                        Toast.LENGTH_SHORT
                    ).show()
                    sharedPref.setLogin(email)
                    sharedPref.setPassword(pass)
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

//    private fun getID(): Int {
//        var id = BEGIN_ID
//        while (true) {
//            if (listMail.isNotEmpty() && listMail.groupBy { it.id }
//                    .keys.indexOf(id) != IS_NOT_FIND) {
//                id++
//            } else {
//                break
//            }
//        }
//        return id
//    }
}