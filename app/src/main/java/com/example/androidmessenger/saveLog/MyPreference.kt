package com.example.androidmessenger.saveLog

import android.content.Context
import android.content.SharedPreferences

class MyPreference(
    context: Context
//    myPref = context.getSharedPreferences("myPref",0)
) {
    val myPref: SharedPreferences = context.getSharedPreferences("myPref", 0)
    var prefEditor: SharedPreferences.Editor = myPref.edit()
    fun setLogin(login: String) {
        prefEditor = myPref.edit();
        prefEditor.putString("login", login);
        prefEditor.commit();
    }

    fun getLogin(): String? {
        return myPref.getString("login", null);
    }
    fun delLogin() {
        prefEditor = myPref.edit();
        prefEditor.remove("login")
        prefEditor.commit();
    }
    fun setPassword(userPass: String) {
        prefEditor = myPref.edit();
        prefEditor.putString("user", userPass);
        prefEditor.commit();
    }

    fun getPassword(): String? {
        return myPref.getString("user", null);
    }
    fun delPassword() {
        prefEditor = myPref.edit();
        prefEditor.remove("user")
        prefEditor.commit();
    }
}