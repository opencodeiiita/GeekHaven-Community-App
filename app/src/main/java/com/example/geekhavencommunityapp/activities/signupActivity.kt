package com.example.geekhavencommunityapp.activities


import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.geekhavencommunityapp.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore


class signupActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    private lateinit var launcher: ActivityResultLauncher<IntentSenderRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val signinTxt = findViewById<TextView>(R.id.signin)
        val signupButton = findViewById<Button>(R.id.signupBtn)
        val signupGoogle: ImageView = findViewById(R.id.signupGoogle)

        val email: TextInputEditText = findViewById(R.id.signupEmail)
        val password: TextInputEditText = findViewById(R.id.signupPassword)
        val name: TextInputEditText = findViewById(R.id.signupName)

        signinTxt.setOnClickListener {
            startActivity(Intent(this, signinActivity::class.java))
        }

        signupButton.setOnClickListener {
            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext, "Account created successfully", Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this, usernameActivity::class.java))
                    } else {
                        Toast.makeText(
                            baseContext, "Failed to create account", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        oneTapClient = Identity.getSignInClient(this)

        signupGoogle.setOnClickListener {
            signInRequest = BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder().setSupported(true)
                        .setServerClientId(getString(R.string.server_client_id))
                        .setFilterByAuthorizedAccounts(false).build()
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
                        Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
                        Log.d(TAG, e.localizedMessage)
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
                                        TAG, "signInWithCredential:failure", task.exception
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