package com.example.quizwiz3

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.quizwiz3.R.id.signIn
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.SignInButton
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.util.concurrent.Executor

class Login : AppCompatActivity() {

    private var gso: GoogleSignInOptions? = null
    private var gsc: GoogleSignInClient? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var tvRedirectSignUp: TextView
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var executor: Executor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        FirebaseApp.initializeApp(this)

        // Initialize Google Sign-In
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_client_id))
            .requestEmail()
            .build()
        gsc = GoogleSignIn.getClient(this, gso!!)

        val googleSignInButton = findViewById<SignInButton>(R.id.signIn)
        googleSignInButton.setOnClickListener { signIn() }

        // Firebase Auth
        auth = FirebaseAuth.getInstance()
        etEmail = findViewById(R.id.etEmailAddress)
        etPassword = findViewById(R.id.etPassword)
        btnSignUp = findViewById(R.id.btnLogin)
        tvRedirectSignUp = findViewById(R.id.tvRedirectSignUp)

        btnSignUp.setOnClickListener {
            login()
        }

        tvRedirectSignUp.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Initialize Biometric Prompt
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                // On successful biometric authentication, navigate to the dashboard
                Toast.makeText(applicationContext, "Biometric authentication succeeded", Toast.LENGTH_SHORT).show()
                navigateToSecondActivity()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(applicationContext, "Biometric authentication failed", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(applicationContext, "Error: $errString", Toast.LENGTH_SHORT).show()
            }
        })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Login for QuizWiz")
            .setSubtitle("Use your biometric credentials to log in")
            .setNegativeButtonText("Cancel")
            .build()

        checkBiometricSupport()

        // Trigger biometric authentication when the biometric login button is clicked
        val btnBiometricLogin = findViewById<Button>(R.id.btnBiometricLogin)
        btnBiometricLogin.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }

    private fun login() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, Dashboard::class.java))
                finish()
            } else {
                Toast.makeText(this, "Incorrect Details. Please try again", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun signIn() {
        gsc?.signOut()?.addOnCompleteListener {
            val signInIntent = gsc!!.signInIntent
            startActivityForResult(signInIntent, 1000)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                Toast.makeText(applicationContext, "Signed in", Toast.LENGTH_SHORT).show()
                firebaseAuthWithGoogle(account.idToken)
            } catch (e: ApiException) {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                navigateToSecondActivity()
            } else {
                Toast.makeText(this, "Firebase Authentication Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun navigateToSecondActivity() {
        finish()
        val intent: Intent = Intent(this@Login, Dashboard::class.java)
        startActivity(intent)
    }

    private fun checkBiometricSupport() {
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Toast.makeText(this, "Biometric features are available", Toast.LENGTH_SHORT).show()
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Toast.makeText(this, "No biometric features available on this device", Toast.LENGTH_SHORT).show()
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Toast.makeText(this, "Biometric features are currently unavailable", Toast.LENGTH_SHORT).show()
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Toast.makeText(this, "Please set up biometric credentials", Toast.LENGTH_SHORT).show()
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL)
                startActivity(enrollIntent)
            }
        }
    }
}
