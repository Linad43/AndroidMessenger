package com.example.androidmessenger.saveLog;

import android.util.Pair;

import com.example.androidmessenger.chatAdapter.ElementChat;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class PersonJ implements Serializable {
    private String uid;
    private String login;
    private String firstName;
    private String secondName;
    private String role;
    private String address;
    private String age;
    private String numPhone;
//    public Map<String,ArrayList<ElementChat>> messages;
//    private Map<String, ArrayList<Pair<KeyMessage, String>>> arrayMessages;

    public static String nameForBundle = "Person";

    public PersonJ() {
        this.uid = FirebaseAuth.getInstance().getUid();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNumPhone() {
        return numPhone;
    }

    public void setNumPhone(String numPhone) {
        this.numPhone = numPhone;
    }

//    public Messages getMessages() {
//        return messages;
//    }
//
//    public void setMessages(Messages messages) {
//        this.messages = messages;
//    }
//
//    public Map<String, ArrayList<Pair<KeyMessage, String>>> getArrayMessages() {
//        return arrayMessages;
//    }
//
//    public void setArrayMessages(Map<String, ArrayList<Pair<KeyMessage, String>>> arrayMessages) {
//        this.arrayMessages = arrayMessages;
//    }
}
