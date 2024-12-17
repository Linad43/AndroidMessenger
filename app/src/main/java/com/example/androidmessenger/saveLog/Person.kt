package com.example.androidmessenger.saveLog

class Person(
    val login: String = randLogin(),
    val firstName: String = "",
    val secondName: String = "",
    val role: String = "",
    val adress: String = "",
    val age: String = "",
) {
    companion object {
        fun randLogin(): String {
            var result = ""
            for (i in 1..8) {
                result = "$result${LETTER.random()}"
            }
            return result
        }
        const val LETTER = "qwertyuiopasdfghjklzxcvbnm0123456789"
    }
}