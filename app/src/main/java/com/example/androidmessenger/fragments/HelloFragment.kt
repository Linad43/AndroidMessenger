package com.example.androidmessenger.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.androidmessenger.R
import com.example.androidmessenger.saveLog.MyPreference


class HelloFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hello, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var sharedPref = MyPreference(requireContext())
//        Handler().postDelayed({
//            if (sharedPref.getLogin() != null && sharedPref.getPassword() != null){
//                findNavController().navigate(R.id.action_helloFragment_to_menuFragment)
//            }else{
//                findNavController().navigate(R.id.action_helloFragment_to_logInFragment)}
//        }, 3710)
        if (sharedPref.getLogin() != null && sharedPref.getPassword() != null){
            findNavController().navigate(R.id.action_helloFragment_to_menuFragment)
        }else{
            findNavController().navigate(R.id.action_helloFragment_to_logInFragment)}
    }
}