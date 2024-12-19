package com.example.androidmessenger.saveLog

import android.annotation.SuppressLint
import com.example.androidmessenger.service.PersonsAdapter
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database

class RWDatadase {
    companion object{
        @SuppressLint("NotifyDataSetChanged")
        fun readUsers(listLog: ArrayList<Person>, adapter: PersonsAdapter) {
            Firebase.database.reference.child("users")
                .get().addOnSuccessListener {
                    for (element in it.children) {
                        val user = element.getValue(Person::class.java)!!
                        if (user.uid == FirebaseAuth.getInstance().uid){
                            continue
                        }
                        listLog.add(user)
                    }
                    adapter.notifyDataSetChanged()
                }
        }
        fun sendPersonToFirebase(person: Person) {
            val database = Firebase.database.reference
                .child("users")
            val map: HashMap<String, Person> = HashMap()
            map[person.uid.toString()] = person
            database.updateChildren(map as Map<String, Any>)
        }
    }
}