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


class profile : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val viewPager: ViewPager = view.findViewById(R.id.profileViewPager)
        val tabLayout: TabLayout = view.findViewById(R.id.profileTabLayout)

        val adapter = FragmentAdapter(childFragmentManager) // Use childFragmentManager instead of supportFragmentManager

        adapter.addFragment(PostFragment(), "Post")
        adapter.addFragment(LikeFragment(), "Like")
        adapter.addFragment(FollowingFragment(), "Following")

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        return view
    }

}