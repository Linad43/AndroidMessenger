package com.example.androidmessenger.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidmessenger.databinding.FragmentMainScreenBinding
import com.example.androidmessenger.service.MainScreenPager
import com.google.android.material.tabs.TabLayoutMediator

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
        adapter = MainScreenPager(this, fragments)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab, position ->
            tab.text = fragments[position].first
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}