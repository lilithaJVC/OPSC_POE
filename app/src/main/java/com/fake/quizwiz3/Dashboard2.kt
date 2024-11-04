package com.fake.quizwiz3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Dashboard2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //declare variables
        //code attribution
        // Adapted from W3Schools' tutorial on Android Button Clicks
        //https://www.w3schools.com/cssref/tryit.php?filename=trycss_js_background-color
        val animalbtn: ImageButton = findViewById(R.id.animalbtn)
        val btnfood: ImageButton = findViewById(R.id.btnfood)
        val btndisney2: ImageButton = findViewById(R.id.btndisney2)
        val btnmusic: ImageButton = findViewById(R.id.btnmusic)
        val btntvshows: ImageButton = findViewById(R.id.btntvshows)
        val btnhistory: ImageButton = findViewById(R.id.btnhistory)

        val toolbar: Toolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(toolbar)


        //code attributution
        //this code was taken from youtube : https://youtu.be/JOdWT50bWw4?si=zKNUSyQAKs9WAqgW

        animalbtn.setOnClickListener {
            Log.d("Dashboard", "Animal button clicked, launching MultiChoice")
            val intent = Intent(this, SingleMultigame::class.java)
            intent.putExtra("category", "Animals")
            startActivity(intent)
        }


        btnfood.setOnClickListener {
            val intent = Intent(this, SingleMultigame::class.java)
            intent.putExtra("category", "Food")
            startActivity(intent)
        }

        btnmusic.setOnClickListener {
            val intent = Intent(this, SingleMultigame::class.java)
            intent.putExtra("category", "Music")
            startActivity(intent)
        }

        btndisney2.setOnClickListener {
            Log.d("Dashboard", "Disney button clicked, launching trueorfalseSingleMode")
            val intent = Intent(this, TrueorFalse2::class.java)
            intent.putExtra("category", "Disney")
            startActivity(intent)
        }

        btntvshows.setOnClickListener {
            Log.d("Dashboard", "TV Shows button clicked, launching trueorfalseSingleMode")
            val intent = Intent(this, TrueorFalse2::class.java)
            intent.putExtra("category", "TV Shows")
            startActivity(intent)
        }

        btnhistory.setOnClickListener {
            Log.d("Dashboard", "History button clicked, launching trueorfalseSingleMode")
            val intent = Intent(this, TrueorFalse2::class.java)
            intent.putExtra("category", "History")
            startActivity(intent)

        }
    }

    //code attribution
    // Adapted from W3Schools' tutorial on Android Button Clicks
//https://www.w3schools.com/cssref/tryit.php?filename=trycss_js_background-color
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
                startActivity(Intent(this,About::class.java))
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



