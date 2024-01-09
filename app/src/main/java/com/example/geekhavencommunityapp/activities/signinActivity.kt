package com.example.geekhavencommunityapp.activities

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.geekhavencommunityapp.R
import com.example.geekhavencommunityapp.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class signinActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        auth = Firebase.auth

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val next = findViewById<Button>(R.id.next_btn)
        val signupTxt = findViewById<TextView>(R.id.signup)


        signupTxt.setOnClickListener {
            startActivity(Intent(this, signupActivity::class.java))
        }

        Log.d("Lets check it out", UserModel.username?:"null string recieved")


        next.setOnClickListener{


            UserModel.setEmail(email.text.toString())
            val currentIntent = intent
//            val intent = Intent(
//                this,
//                passwordActivity:: class.java
//            )
//
//                startActivity(
//                    intent
//                )
            auth.signInWithEmailAndPassword(UserModel.email!! , password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(ContentValues.TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        startActivity(Intent(this, BaseHomeActivity::class.java))

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }

        }
    }
}

