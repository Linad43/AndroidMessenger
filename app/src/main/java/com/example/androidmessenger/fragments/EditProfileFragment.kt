package com.example.androidmessenger.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidmessenger.databinding.FragmentEditProfileBinding
import com.example.androidmessenger.saveLog.MyPreference
import com.example.androidmessenger.saveLog.Person
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database

class EditProfileFragment : Fragment() {

    val GALLERY_REQUEST = 1
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var sharedPref = MyPreference(requireContext())

        readInfo()
        binding.loginTV.text = sharedPref.getLogin()
        binding.cardImage.setOnClickListener {
            setImage(GALLERY_REQUEST)
        }
        binding.imageButton.setOnClickListener {
            setImage(GALLERY_REQUEST)
        }
        binding.saveBTN.setOnClickListener {
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
            var adress: String? = null
            var age: String? = null
            var i = 1
            listET.forEach {
                if (it.text.isNotEmpty()) {
                    when (i) {
                        1 -> firstName = it.text.toString()
                        2 -> secondName = it.text.toString()
                        3 -> role = it.text.toString()
                        4 -> adress = it.text.toString()
                        5 -> age = it.text.toString()
                    }

                }
                i++
            }
            val person = Person(
                login = sharedPref.getLogin().toString(),
                firstName = firstName?:"",
                secondName = secondName?:"",
                role = role?:"",
                adress = adress?:"",
                age = age?:""
            )
            addInfo(person)
        }
    }

    private fun addInfo(infoPerson: Person) {
        val id = FirebaseAuth.getInstance().currentUser!!.uid
        val database = Firebase.database.reference
            .child("users")
            .child(id)
        val map: HashMap<String, Person> = HashMap()
        map["Person"] = infoPerson
        database.updateChildren(map as Map<String, Any>)
    }
    private fun readInfo() {
        val id = FirebaseAuth.getInstance().currentUser!!.uid
        Firebase.database.reference.child("users")
            .child(id).get().addOnSuccessListener {
                if (it.child("Person").getValue(Person::class.java) != null) {
                    var person = it.child("Person").getValue(Person::class.java)!!
                    binding.firstNameET.setText(person.firstName)
                    binding.secondNameET.setText(person.secondName)
                    binding.roleET.setText(person.role)
                    binding.adressET.setText(person.adress)
                    binding.ageET.setText(person.age.toString())
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


private fun EditProfileFragment.setImage(GALLERY_REQUEST: Int) {
    val imagePickerIntent = Intent(Intent.ACTION_PICK)
    imagePickerIntent.type = "image/*"
    startActivityForResult(imagePickerIntent, GALLERY_REQUEST)
}