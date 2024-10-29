package com.example.quizwiz3

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Results2 : AppCompatActivity() {

    private lateinit var player1ScoreTextView: TextView
    private lateinit var player2ScoreTextView: TextView
    private lateinit var winnerTextView: TextView
    private lateinit var player1ScoreBar: ProgressBar
    private lateinit var player2ScoreBar: ProgressBar
    private lateinit var player1NameTextView: TextView
    private lateinit var player2NameTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_results2)

       // val toolbar: Toolbar = findViewById(R.id.toolbar)
        //setSupportActionBar(toolbar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        player1ScoreTextView = findViewById(R.id.player1ScoreTextView)
        player2ScoreTextView = findViewById(R.id.player2ScoreTextView)
        winnerTextView = findViewById(R.id.winnerTextView)
        player1ScoreBar = findViewById(R.id.player1ScoreBar)
        player2ScoreBar = findViewById(R.id.player2ScoreBar)
        player1NameTextView = findViewById(R.id.player1NameTextView)
        player2NameTextView = findViewById(R.id.player2NameTextView)

        // Retrieve scores and player names from intent extras
        val player1Score = intent.getIntExtra("player1Score", 0)
        val player2Score = intent.getIntExtra("player2Score", 0)
        val player1Name = intent.getStringExtra("player1Name") ?: "Player 1"
        val player2Name = intent.getStringExtra("player2Name") ?: "Player 2"

        // Display the scores
        player1ScoreTextView.text = "$player1Name: $player1Score"
        player2ScoreTextView.text = "$player2Name: $player2Score"

        // Set the player names above the progress bars
        player1NameTextView.text = player1Name
        player2NameTextView.text = player2Name

        // Set the progress bars
        player1ScoreBar.progress = player1Score // Adjust this based on the max score
        player2ScoreBar.progress = player2Score // Adjust this based on the max score

        // Determine and display the winner
        displayWinner(player1Score, player2Score, player1Name, player2Name)
    }

    /**
     * Displays the winner based on the scores.
     */
    private fun displayWinner(player1Score: Int, player2Score: Int, player1Name: String, player2Name: String) {
        when {
            player1Score > player2Score -> winnerTextView.text = "$player1Name Wins!"
            player2Score > player1Score -> winnerTextView.text = "$player2Name Wins!"
            else -> winnerTextView.text = "It's a Draw!"
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
                startActivity(Intent(this, com.example.quizwiz3.Settings::class.java))
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
            R.id.multilanguage -> {
                val alertDialog = AlertDialog.Builder(this)
                    .setTitle(getString(R.string.multititle))
                    .setMessage(getString(R.string.language_popup))
                    .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                        dialog.dismiss() // Dismiss the dialog when the user clicks OK
                    }
                    .create()

                alertDialog.show()

            }
        }
        return super.onOptionsItemSelected(item)
    }

}
