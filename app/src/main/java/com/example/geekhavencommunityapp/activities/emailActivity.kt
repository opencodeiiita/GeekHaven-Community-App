package com.example.geekhavencommunityapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import com.example.geekhavencommunityapp.R
import com.example.geekhavencommunityapp.UserModel

class emailActivity : AppCompatActivity() {

    private val userModel  : UserModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email)


        val next = findViewById<Button>(R.id.next_btn)
        val email = findViewById<EditText>(R.id.email)

        // this returns empty string
        Log.d("IDK", userModel.email)


        next.setOnClickListener{

            userModel.setEmail(email.text.toString())
            val intent = Intent(
                this,
                passwordActivity:: class.java
            )

            intent.putExtra("email" , email.text.toString())

                Log.d("usernae", getIntent().getStringExtra("username")!!)
                intent.putExtra("username", getIntent().getStringExtra("username"))

                startActivity(
                    intent
                )

        }

    }
}