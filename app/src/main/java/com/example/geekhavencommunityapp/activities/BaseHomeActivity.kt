package com.example.geekhavencommunityapp.activities

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.GestureDetector
import android.view.Gravity
import android.view.MenuItem
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.geekhavencommunityapp.R
import com.example.geekhavencommunityapp.fragments.community
import com.example.geekhavencommunityapp.fragments.feed
import com.example.geekhavencommunityapp.fragments.Home
import com.example.geekhavencommunityapp.fragments.profile
import com.example.geekhavencommunityapp.fragments.project
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class BaseHomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val fragment1 = Home()
    private val fragment2 = project()
    private val fragment3 = community()
    private val fragment4 = profile()

    private lateinit var gestureDetector: GestureDetector
    private lateinit var drawerLayout: DrawerLayout

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_item1 -> {
                    loadFragment(fragment1)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_item2 -> {
                    loadFragment(fragment2)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_item3 -> {
                    loadFragment(fragment3)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_item4 -> {
                    loadFragment(fragment4)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gestureDetector = GestureDetector(this, GestureListener())

        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        // Default fragment on startup
        loadFragment(fragment1)

        drawerLayout = findViewById(R.id.drawer_layout)
        val sidebarButton: ImageButton = findViewById(R.id.sidebarButton)

        sidebarButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val addButton: ImageButton = findViewById(R.id.AddButton)
        addButton.setOnClickListener {
            showBottomLayout()
        }
    }

    private inner class GestureListener : GestureDetector.OnGestureListener{
        private val SWIPE_THRESHOLD = 100
        override fun onDown(p0: MotionEvent): Boolean {
            TODO("Not yet implemented")
        }

        override fun onShowPress(p0: MotionEvent) {
            TODO("Not yet implemented")
        }

        override fun onSingleTapUp(p0: MotionEvent): Boolean {
            TODO("Not yet implemented")
        }

        override fun onScroll(p0: MotionEvent?, p1: MotionEvent, p2: Float, p3: Float): Boolean {
            TODO("Not yet implemented")
        }

        override fun onLongPress(p0: MotionEvent) {
            TODO("Not yet implemented")
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            // Check if the swipe is from left to right
            if (e1 != null && e1.x < e2.x && e2.x - e1.x > SWIPE_THRESHOLD) {
                // Open the drawer
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
            return false
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> replaceFragment(feed())
            R.id.nav_project -> replaceFragment(project())
            R.id.nav_profile -> replaceFragment(profile())
            R.id.nav_community -> replaceFragment(community())
            R.id.nav_developer -> Toast.makeText(this, "ParadoxNJ005!", Toast.LENGTH_SHORT).show()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }

    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit()
    }

    private fun showBottomLayout() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.bottom_sheet_layout)

        val closeButton: ImageView = dialog.findViewById(R.id.imageViewClose)
        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }
}
