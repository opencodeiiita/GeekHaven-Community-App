package com.example.geekhavencommunityapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.geekhavencommunityapp.activities.BaseHomeActivity
import com.example.geekhavencommunityapp.activities.signinActivity
import com.example.geekhavencommunityapp.activities.usernameActivity
import com.example.geekhavencommunityapp.fragments.Intro1
import com.example.geekhavencommunityapp.fragments.Intro2
import com.example.geekhavencommunityapp.fragments.Intro3
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class OnboardingPage : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    override fun onStart() {
        super.onStart()


        auth = Firebase.auth

        if(auth.currentUser != null)
        {
            startActivity(Intent(this, BaseHomeActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_page)

        val fragmentList = arrayListOf<Fragment>(
            Intro1(),
            Intro2(),
            Intro3()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            supportFragmentManager,
            lifecycle
        )

        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        viewPager.adapter = adapter

        val indicator = findViewById<DotsIndicator>(R.id.dots_indicator)
        indicator.attachTo(viewPager)

        // skip button
        val skip: TextView = findViewById(R.id.btnSkip)
        skip.setOnClickListener {
            val intent = Intent(this, signinActivity::class.java)
            startActivity(intent)
        }

        val next: ImageView = findViewById(R.id.btnNext)
        next.setOnClickListener {
            val viewPager = findViewById<ViewPager2>(R.id.view_pager)
            if (viewPager.currentItem == 2) {
                val intent = Intent(this, signinActivity::class.java)
                startActivity(intent)
            }
            viewPager.currentItem = viewPager.currentItem + 1
        }
    }
}

class ViewPagerAdapter(private val fragmentList: ArrayList<Fragment>, fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}