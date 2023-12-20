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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpTabs()
    }


    private fun setUpTabs() {

        val adapter = FragmentAdapter(childFragmentManager)
        adapter.addFragment(PostFragment(), "Post")
        adapter.addFragment(LikeFragment(), "Like")
        adapter.addFragment(FollowingFragment(), "Following")

        val viewPager: ViewPager? = view?.findViewById(R.id.viewPagerProfile)
        viewPager?.adapter = adapter

        val tabLayout: TabLayout? = view?.findViewById(R.id.profileAppBarLayout)
        tabLayout?.setupWithViewPager(viewPager)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
}