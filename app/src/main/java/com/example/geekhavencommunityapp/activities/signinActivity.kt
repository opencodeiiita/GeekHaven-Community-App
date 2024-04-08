package com.example.geekhavencommunityapp.activities

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.geekhavencommunityapp.OnboardingPage
import com.example.geekhavencommunityapp.R
import com.example.geekhavencommunityapp.UserModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.json.JSONObject

class signinActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    private lateinit var launcher: ActivityResultLauncher<IntentSenderRequest>
    private lateinit var requestQueue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        auth = Firebase.auth
        requestQueue = Volley.newRequestQueue(this)

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val next = findViewById<Button>(R.id.next_btn)
        val signupTxt = findViewById<TextView>(R.id.signup)
        val siginGoogle: ImageView = findViewById(R.id.signinGoogle)


        signupTxt.setOnClickListener {
            startActivity(Intent(this, signupActivity::class.java))
        }



        val backButton = findViewById<ImageView>(R.id.backButtonSignIn)
        backButton.setOnClickListener {
            val intent = Intent(this, OnboardingPage::class.java)
            startActivity(intent)
        }

        Log.d("Lets check it out", UserModel.username?:"null string recieved")


        next.setOnClickListener {
            val url = "http://your-backend-url/signin"
            val jsonBody = JSONObject()
            jsonBody.put("email", email.text.toString())
            jsonBody.put("password", password.text.toString())

            val request = JsonObjectRequest(
                Request.Method.POST, url, jsonBody,
                { response ->
                    // Handle successful signin
                    val token = response.getString("token")
                    Toast.makeText(this, "Signed in successfully!", Toast.LENGTH_SHORT).show()
                    // Proceed to the next activity or perform actions as needed
                },
                { error ->
                    // Handle unsuccessful signin
                    Toast.makeText(this, "Signin failed: ${error.message}", Toast.LENGTH_SHORT)
                        .show()
                })

            requestQueue.add(request)
        }



        oneTapClient = Identity.getSignInClient(this)

        siginGoogle.setOnClickListener {
            signInRequest = BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder().setSupported(true)
                    .setServerClientId(getString(R.string.server_client_id))
                    .setFilterByAuthorizedAccounts(true).build()
            ).setAutoSelectEnabled(true).build()

            oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this, OnSuccessListener<BeginSignInResult> { result ->
                    Toast.makeText(this, "gooo", Toast.LENGTH_SHORT).show()
                    launcher.launch(
                        IntentSenderRequest.Builder(
                            result.pendingIntent.intentSender
                        ).build()
                    )
                }).addOnFailureListener(this,
                    OnFailureListener { e -> // No Google Accounts found. Just continue presenting the signed-out UI.
                        Toast.makeText(this, "User not found, sign up first", Toast.LENGTH_SHORT).show()
                        Log.d(ContentValues.TAG, e.localizedMessage)
                    })
        }

        launcher = registerForActivityResult<IntentSenderRequest, ActivityResult>(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                    val idToken = credential.googleIdToken
                    if (idToken != null) {
                        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                        auth.signInWithCredential(firebaseCredential)
                            .addOnCompleteListener(this, OnCompleteListener<AuthResult?> { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                                    val user = auth.currentUser
                                    startActivity(
                                        Intent(
                                            this, usernameActivity::class.java
                                        )
                                    )

                                } else {
                                    Toast.makeText(this, "failure", Toast.LENGTH_SHORT).show()

                                    // If sign in fails, display a message to the user.
                                    Log.w(
                                        ContentValues.TAG,
                                        "signInWithCredential:failure",
                                        task.exception
                                    )
                                }
                            })
                    }
                } catch (e: ApiException) {
                }
            }
        }
    }
}

