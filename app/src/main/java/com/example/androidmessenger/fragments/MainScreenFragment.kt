package com.example.androidmessenger.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.androidmessenger.R
import com.example.androidmessenger.databinding.FragmentMainScreenBinding
import com.example.androidmessenger.saveLog.MyPreference
import com.example.androidmessenger.service.MainScreenPager
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

class MainScreenFragment : Fragment() {

    private val fragments = arrayListOf(
        "Чаты" to ViewPagerChatsFragment(),
        "Пользователи" to ViewPagerContactsFragment()
    )
    private lateinit var adapter: MainScreenPager
    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var sharedPref = MyPreference(requireContext())

        adapter = MainScreenPager(this, fragments)
        binding.viewPager.adapter = adapter
        binding.toolbar.setTitle(sharedPref.getLogin())
        binding.toolbar.inflateMenu(R.menu.main_menu)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.exit -> requireActivity().finishAffinity()
                R.id.editProfile -> findNavController().navigate(R.id.action_menuFragment_to_editProfileFragment)
            }
            true
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = fragments[position].first
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}