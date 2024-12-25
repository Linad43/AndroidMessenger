package com.example.androidmessenger.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.androidmessenger.databinding.FragmentEditProfileBinding
import com.example.androidmessenger.saveLog.MyPreference
import com.example.androidmessenger.saveLog.Person
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database
import com.example.androidmessenger.R
import com.example.androidmessenger.saveLog.PersonJ
import com.example.androidmessenger.saveLog.RWDatadase
import kotlin.concurrent.thread

class EditProfileFragment : Fragment() {

    val GALLERY_REQUEST = 1
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private var person: PersonJ? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var sharedPref = MyPreference(requireContext())
        readPersonFromFirebase()
        binding.loginTV.text = sharedPref.getLogin()

        binding.cardImage.setOnClickListener {
            setImage(GALLERY_REQUEST)
        }

        binding.imageButton.setOnClickListener {
            setImage(GALLERY_REQUEST)
        }

        binding.saveBTN.setOnClickListener {
            val person = createPersonFromFragment(sharedPref)
            if (this.person != null) {
                this.person!!.firstName = person.firstName
                this.person!!.secondName = person.secondName
                this.person!!.role = person.role
                this.person!!.address = person.address
                this.person!!.age = person.age
                RWDatadase.sendPersonToFirebase(this.person!!)
            } else{
                RWDatadase.sendPersonToFirebase(person)
            }
        }

        binding.editNumPhone.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())
            val inflater = this.layoutInflater
            val dialogView = inflater.inflate(R.layout.dialog_add_num_phone, null)
            dialog.setView(dialogView)
            val editNumPhone = dialogView.findViewById<EditText>(R.id.inputNumPhoneET)

            dialog.setTitle("Обновить номер телефона")
            dialog.setPositiveButton("Обновить") { _, _ ->
//                var person = readPersonFromFirebase()
                if (person != null) {
                    person!!.numPhone = editNumPhone.text.toString()
                    RWDatadase.sendPersonToFirebase(person!!)
                }
            }
            dialog.setNegativeButton("Отмена") { _, _ ->
            }
            dialog.create().show()
        }
    }

    private fun createPersonFromFragment(sharedPref: MyPreference): PersonJ {
        val listET = arrayListOf(
            binding.firstNameET,
            binding.secondNameET,
            binding.roleET,
            binding.adressET,
            binding.ageET,
        )
        var firstName: String? = null
        var secondName: String? = null
        var role: String? = null
        var address: String? = null
        var age: String? = null
        var i = 1
        listET.forEach {
            if (it.text.isNotEmpty()) {
                when (i) {
                    1 -> firstName = it.text.toString()
                    2 -> secondName = it.text.toString()
                    3 -> role = it.text.toString()
                    4 -> address = it.text.toString()
                    5 -> age = it.text.toString()
                }
            }
            i++
        }
        val person = PersonJ()
        person.login = sharedPref.getLogin().toString()
        person.firstName = firstName ?: ""
        person.secondName = secondName ?: ""
        person.role = role ?: ""
        person.address = address ?: ""
        person.age = age ?: ""
        return person
    }

    private fun readPersonFromFirebase() {
        val id = FirebaseAuth.getInstance().uid!!
        Firebase.database.reference
            .child("users")
            .child(id)
            .get().addOnSuccessListener {
                if (it.getValue(PersonJ::class.java) != null) {
                    person = it.getValue(PersonJ::class.java)!!
                    setPersonToFragment(person!!)
                }
            }
    }

    private fun setPersonToFragment(person: PersonJ) {
        binding.firstNameET.setText(person.firstName)
        binding.secondNameET.setText(person.secondName)
        binding.roleET.setText(person.role)
        binding.adressET.setText(person.address)
        binding.ageET.setText(person.age.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun EditProfileFragment.setImage(GALLERY_REQUEST: Int) {
        val imagePickerIntent = Intent(Intent.ACTION_PICK)
        imagePickerIntent.type = "image/*"
        startActivityForResult(imagePickerIntent, GALLERY_REQUEST)
    }
}



