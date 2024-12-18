package com.example.androidmessenger.saveLog

import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class Person(
    val login: String = Firebase.auth.uid!!,
    val firstName: String = "",
    val secondName: String = "",
    val role: String = "",
    val address: String = "",
    val age: String = "",
    val numPhone: String = "",
) {
//    companion object {
//        fun randLogin(): String {
//            var result = ""
//            for (i in 1..8) {
//                result = "$result${LETTER.random()}"
//            }
//            return result
//        }
//        const val LETTER = "qwertyuiopasdfghjklzxcvbnm0123456789"
//    }
}