package com.example.geekhavencommunityapp.activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
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
import com.example.geekhavencommunityapp.fragments.profile
import com.example.geekhavencommunityapp.fragments.project
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class BaseHomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val fragment1 = feed()
    private val fragment2 = project()
    private val fragment3 = community()
    private val fragment4 = profile()

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

        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        // Default fragment on startup
        loadFragment(fragment1)

        drawerLayout = findViewById(R.id.drawer_layout)
        val sidebarButton: ImageButton = findViewById(R.id.sidebarButton)

        sidebarButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
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
}
