package com.example.androidmessenger.service

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainScreenPager(
    fragment: Fragment,
    private val list: ArrayList<Pair<String, Fragment>>,
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position].second
    }
}