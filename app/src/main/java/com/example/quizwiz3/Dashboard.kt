package com.example.quizwiz3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class Dashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Declare variables for buttons
        val animalbtn: ImageButton = findViewById(R.id.animalbtn)
        val btnfood: ImageButton = findViewById(R.id.btnfood)
        val btnmusic: ImageButton = findViewById(R.id.btnmusic)
        val btndisney: ImageButton = findViewById(R.id.btndisney)
        val btntvshows: ImageButton = findViewById(R.id.btntvshows)
        val btnhistory: ImageButton = findViewById(R.id.btnhistory)

        val toolbar: Toolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(toolbar)

        // Set onClick listeners for buttons
        animalbtn.setOnClickListener {
            launchPlayerSelection("Animals")
        }

        btnfood.setOnClickListener {
            launchPlayerSelection("Food")
        }

        btnmusic.setOnClickListener {
            launchPlayerSelection("Music")
        }

        btndisney.setOnClickListener {
            launchPlayerSelection("Disney")
        }

        btntvshows.setOnClickListener {
            launchPlayerSelection("TV Shows")
        }

        btnhistory.setOnClickListener {
            launchPlayerSelection("History")
        }
    }

    private fun launchPlayerSelection(category: String) {
        Log.d("Dashboard", "$category button clicked, launching PlayerSelection")
        val intent = Intent(this, PlayerDetails::class.java)
        intent.putExtra("category", category)
        startActivity(intent)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile -> {
                startActivity(Intent(this, Profile::class.java))
                true
            }
            R.id.dashboard -> {
                startActivity(Intent(this, PlayerSelection::class.java))
                true
            }
            R.id.settings -> {
                startActivity(Intent(this, Settings::class.java))
                true
            }
            R.id.helpsupport -> {
                startActivity(Intent(this, HelpSupport::class.java))
                true
            }
            R.id.about -> {
                startActivity(Intent(this, About::class.java))
                true
            }
            R.id.logout -> {
                startActivity(Intent(this, Logout::class.java))
                true
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
