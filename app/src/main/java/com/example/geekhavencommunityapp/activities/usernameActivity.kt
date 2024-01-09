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
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class usernameActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_username)

        auth = Firebase.auth

        // for testing logout
//        if(auth.currentUser != null)
//        {
//            auth.signOut()
//        }



        val next = findViewById<Button>(R.id.next_btn)
        val username = findViewById<EditText>(R.id.username)


        next.setOnClickListener{

            UserModel.setUsername(username.text.toString())
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
                BaseHomeActivity:: class.java)



            startActivity(intent)

        }


    }
}