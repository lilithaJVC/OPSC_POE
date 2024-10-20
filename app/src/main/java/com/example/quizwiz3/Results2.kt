package com.example.quizwiz3

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
}
