package com.example.quizwiz3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PlayerDetails : AppCompatActivity() {
    private lateinit var selectedCategory: String // Variable to hold the selected category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_details)

        // Initialize Toolbar
        val toolbar: Toolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(toolbar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get references to the EditTexts and Buttons
        val player1EditText = findViewById<EditText>(R.id.player1txtview)
        val player2EditText = findViewById<EditText>(R.id.player2txtview)
        val startButton = findViewById<Button>(R.id.startbtn)

        // Get the selected category from the Intent
        selectedCategory = intent.getStringExtra("category") ?: "Animals"

        // Log the selected category for debugging
        Log.d("PlayerDetails", "Selected category: $selectedCategory")

        // Set the click listener for the Start button
        startButton.setOnClickListener {
            // Get the text from the EditText views
            val player1Name = player1EditText.text.toString()
            val player2Name = player2EditText.text.toString()

            // Check if both fields are filled
            if (player1Name.isNotEmpty() && player2Name.isNotEmpty()) {
                // Show an alert dialog confirming players are ready
                showPlayersReadyDialog(player1Name, player2Name, selectedCategory) // Pass the category here
            } else {
                // Show a toast if either of the fields is empty
                Toast.makeText(this, "Please enter both players' names", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showPlayersReadyDialog(player1: String, player2: String, category: String) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("$player1 and $player2 are ready to go!")
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss() // Dismiss the dialog when "OK" is clicked

                // Log the category for debugging
                Log.d("PlayerSelection", "Selected category: $category")

                // Start the appropriate activity based on the selected category
                val intent = when (category) {
                    "Disney", "History", "TV Shows" -> Intent(this, TrueorFalse::class.java)
                    else -> Intent(this, MultiChoice::class.java) // Default to MultiChoice
                }.apply {
                    putExtra("category", category)
                    putExtra("player1Name", player1) // Pass player 1 name
                    putExtra("player2Name", player2) // Pass player 2 name
                }

                startActivity(intent)
                finish() // Optionally finish this activity if you don't want to go back to it
            }
        val alert = dialogBuilder.create()
        alert.setTitle("Players Ready")
        alert.show()
    }
    /**
     * Inflates the menu options.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    /**
     * Handles menu item selections.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile -> {
                startActivity(Intent(this, Profile::class.java))
                return true
            }

            R.id.dashboard -> {
                val intent = Intent(this, PlayerSelection::class.java)
                startActivity(intent)
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
                Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, Logout::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

