package com.example.geekhavencommunityapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.geekhavencommunityapp.FragmentAdapter
import com.example.geekhavencommunityapp.R
import com.google.android.material.tabs.TabLayout

class community : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_community, container, false)

        val viewPager: ViewPager = view.findViewById(R.id.viewPager)
        val tabLayout: TabLayout = view.findViewById(R.id.tablayout)

        val fragmentAdapter = FragmentAdapter(childFragmentManager) // Use childFragmentManager instead of supportFragmentManager
        fragmentAdapter.addFragment(activity(), "Activity") // Assuming ActivityFragment and MessagesFragment are the appropriate fragment classes
        fragmentAdapter.addFragment(messages(), "Messages")

        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)

        return view
    }
}
