package com.example.geekhavencommunityapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.example.geekhavencommunityapp.R

/**
 * A simple [Fragment] subclass.
 * Use the [Intro1.newInstance] factory method to
 * create an instance of this fragment.
 */
class Intro1 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_intro1, container, false)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)
        return view
    }
}