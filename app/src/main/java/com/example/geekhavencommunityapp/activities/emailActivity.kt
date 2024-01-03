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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email)


        val next = findViewById<Button>(R.id.next_btn)
        val email = findViewById<EditText>(R.id.email)

        Log.d("Lets check it out", UserModel.username?:"null string recieved")


        next.setOnClickListener{
            UserModel.setEmail(email.text.toString())

            val intent = Intent(
                this,
                passwordActivity:: class.java
            )

                startActivity(
                    intent
                )

        }

    }
}