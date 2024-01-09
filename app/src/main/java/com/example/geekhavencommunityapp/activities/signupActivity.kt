package com.example.geekhavencommunityapp.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.geekhavencommunityapp.R


class signupActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val signinTxt = findViewById<TextView>(R.id.signin)
        val signupButton = findViewById<Button>(R.id.signupBtn)

        signinTxt.setOnClickListener {
            startActivity(Intent(this, signinActivity::class.java))
        }

        signupButton.setOnClickListener {
            startActivity(Intent(this, usernameActivity::class.java))
        }
    }
}