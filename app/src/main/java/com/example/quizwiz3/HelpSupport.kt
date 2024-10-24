package com.example.quizwiz3

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.androidgamesdk.gametextinput.Settings

class HelpSupport : AppCompatActivity() {

    private lateinit var btnSubmitFeedback: Button
    private lateinit var editTextFeedback: EditText
    private lateinit var textViewFAQ: TextView
    private lateinit var btnBackSettings: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_support) // Ensure you are linking the correct XML layout file

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        // Initialize views
        btnBackSettings = findViewById(R.id.btnBackSettings)
        btnSubmitFeedback = findViewById(R.id.btnSubmitFeedback)
        editTextFeedback = findViewById(R.id.editTextFeedback)
        textViewFAQ = findViewById(R.id.textViewFAQ)

        // Back to Settings Menu Button
        btnBackSettings = findViewById(R.id.btnBackSettings)
        btnBackSettings.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }
        // Set up FAQ text

        textViewFAQ.text =
            "Frequently Asked Questions:\n1. How to reset my password?\n2. How to contact support?\n3. How to change my email?"


        // Set OnClickListener for submit feedback button
        btnSubmitFeedback.setOnClickListener {
            submitFeedback()
        }



    }

    private fun submitFeedback() {
        val feedback = editTextFeedback.text.toString()

        if (feedback.isNotEmpty()) {
            // Here, you can add your logic to save the feedback (e.g., send to server, save locally, etc.)

            // Show a confirmation message
            Toast.makeText(this, "FAQ and feedback submitted!", Toast.LENGTH_SHORT).show()

            // Optionally, clear the feedback input
            editTextFeedback.text.clear()
        } else {
            Toast.makeText(this, "Please enter your feedback.", Toast.LENGTH_SHORT).show()



        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile -> {
                startActivity(Intent(this, Profile::class.java))
                return true
            }

            R.id.dashboard -> {
                startActivity(Intent(this, PlayerSelection::class.java))
                return true
            }

            R.id.settings -> {
                startActivity(Intent(this, Settings::class.java))
                return true
            }

            R.id.helpsupport -> {
                startActivity(Intent(this, HelpSupport::class.java))
                return true
            }

            R.id.about -> {
                startActivity(Intent(this, About::class.java))
                return true
            }

            R.id.logout -> {
                startActivity(Intent(this, Logout::class.java))

            }
        }
        return super.onOptionsItemSelected(item)
    }
}



