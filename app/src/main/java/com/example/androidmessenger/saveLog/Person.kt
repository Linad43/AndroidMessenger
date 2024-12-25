package com.example.androidmessenger.saveLog

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.io.Serializable

class Person(
    val uid: String = Firebase.auth.uid!!,
    val login: String = "",
    val firstName: String = "",
    val secondName: String = "",
    val role: String = "",
    val address: String = "",
    val age: String = "",
    val numPhone: String = "",
    val arrayMessages: MutableMap<String, ArrayList<Pair<KeyMessage, String>>> = mutableMapOf(),
) : Serializable {
    companion object {
        const val nameForBundle = "Person"
        //        fun randLogin(): String {
//            var result = ""
//            for (i in 1..8) {
//                result = "$result${LETTER.random()}"
//            }
//            return result
//        }
//        const val LETTER = "qwertyuiopasdfghjklzxcvbnm0123456789"
    }
}