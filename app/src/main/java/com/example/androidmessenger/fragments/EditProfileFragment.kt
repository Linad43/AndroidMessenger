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
import kotlinx.coroutines.joinAll

class EditProfileFragment : Fragment() {

    val GALLERY_REQUEST = 1
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private var person: Person? = null

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
        person = readPersonFromFirebase()
        if (person != null) {
            setPersonToFragment(person!!)
        }

        binding.loginTV.text = sharedPref.getLogin()

        binding.cardImage.setOnClickListener {
            setImage(GALLERY_REQUEST)
        }

        binding.imageButton.setOnClickListener {
            setImage(GALLERY_REQUEST)
        }

        binding.saveBTN.setOnClickListener {
            val person = createPersonFromFragment(sharedPref)
            sendPersonToFirebase(person)
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
                    person = Person(
                        login = person!!.login,
                        firstName = person!!.firstName,
                        secondName = person!!.secondName,
                        role = person!!.role,
                        age = person!!.age,
                        address = person!!.address,
                        numPhone = editNumPhone.text.toString()
                    )
                    sendPersonToFirebase(person!!)
                }
            }
            dialog.setNegativeButton("Отмена"){_,_,->
            }
            dialog.create().show()
        }
    }

    private fun createPersonFromFragment(sharedPref: MyPreference): Person {
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
        val person = Person(
            login = sharedPref.getLogin().toString(),
            firstName = firstName ?: "",
            secondName = secondName ?: "",
            role = role ?: "",
            address = address ?: "",
            age = age ?: "",
        )
        return person
    }

    private fun sendPersonToFirebase(person: Person) {
        val id = FirebaseAuth.getInstance().currentUser!!.uid
        val database = Firebase.database.reference
            .child("users")
            .child(id)
        val map: HashMap<String, Person> = HashMap()
        map["Person"] = person
        database.updateChildren(map as Map<String, Any>)
    }

    private fun readPersonFromFirebase(): Person? {
        val id = FirebaseAuth.getInstance().currentUser!!.uid
        var person: Person? = null
        Firebase.database.reference.child("users")
            .child(id).get().addOnSuccessListener {
                if (it.child("Person").getValue(Person::class.java) != null) {
                    person = it.child("Person").getValue(Person::class.java)!!
                    setPersonToFragment(person)
                    this.person = person
                }
            }
        return person
    }

    private fun setPersonToFragment(person: Person) {
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

//    private fun readPersonFromFragment() {
//        val person = Person(
//            login = binding.loginTV.text.toString(),
//            firstName = binding.,
//            secondName = secondName ?: "",
//            role = role ?: "",
//            address = address ?: "",
//            age = age ?: "",
//        )
//    }
}



