package com.fake.quizwiz3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class Logout : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
        FirebaseAuth.getInstance().signOut()
        Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }
}