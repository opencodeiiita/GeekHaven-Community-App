package com.example.geekhavencommunityapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.geekhavencommunityapp.R

class createProject : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_project, container, false)

        val backButton: ImageButton = view.findViewById(R.id.imageButtonBack)
        backButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, project())
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}
