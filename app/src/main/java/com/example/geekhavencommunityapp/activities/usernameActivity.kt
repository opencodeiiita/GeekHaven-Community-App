package com.example.geekhavencommunityapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.geekhavencommunityapp.R
import com.example.geekhavencommunityapp.UserModel

class usernameActivity : AppCompatActivity() {
   private val userModel  : UserModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_username)



        val next = findViewById<Button>(R.id.next_btn)
        val username = findViewById<EditText>(R.id.username)


        next.setOnClickListener{

            userModel.setUsername(username.text.toString())
            if(username.text.toString().trim() == "")
            {
                Toast.makeText(this, "Username field must be non-empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            if(username.text.toString().matches("[a-zA-Z0-9]+".toRegex()))
//            {
//                Toast.makeText(this, "Username field must be alphanumeric", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }

            val intent = Intent(
                this,
                emailActivity:: class.java)

            intent.putExtra("username", username.text.toString())

            startActivity(intent)

        }


    }
}