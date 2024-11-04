package com.fake.quizwiz3

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth


class Settings : AppCompatActivity() {

    private lateinit var btnProfileSettings: Button
    private lateinit var btnGameSettings: Button
    private lateinit var btnHelpSupport: Button
    private lateinit var btnAbout: Button

    private lateinit var btnLogout: Button
    private lateinit var auth: FirebaseAuth


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        FirebaseApp.initializeApp(this)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        // Find buttons by their IDs
        btnProfileSettings = findViewById(R.id.btnProfileSettings)
        btnGameSettings = findViewById(R.id.btnGameSettings)
        btnHelpSupport = findViewById(R.id.btnHelpSupport)
        btnAbout = findViewById(R.id.btnAbout)

        btnLogout = findViewById(R.id.btnBACK)

        auth = FirebaseAuth.getInstance()





        // Set OnClickListeners for the buttons
        btnProfileSettings.setOnClickListener {
            // Start Profile Activity
            val intent = Intent(this, Profile::class.java)

            startActivity(intent)
        }

        btnGameSettings.setOnClickListener {
            // Start GameSettings Activity
            val intent = Intent(this, GameSettings::class.java)
            startActivity(intent)
        }

        btnHelpSupport.setOnClickListener {
            // Start HelpSupport Activity
            val intent = Intent(this, HelpSupport::class.java)
            startActivity(intent)
        }

        btnAbout.setOnClickListener {
            // Start About Activity
            val intent = Intent(this, About::class.java)
            startActivity(intent)
        }


        btnLogout.setOnClickListener {

            val intent = Intent(applicationContext, Dashboard::class.java)
            startActivity(intent)

        }

    }

    // Inflate the menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Handle menu item selection
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile -> {
                val intent = Intent(this, Profile::class.java)
                startActivity(intent)
                return true
            }
            R.id.dashboard -> {
                val intent = Intent(this, PlayerSelection::class.java)
                startActivity(intent)
                return true
            }
            R.id.settings -> {
                val intent = Intent(this, Settings::class.java)
                startActivity(intent)
                return true
            }
            R.id.helpsupport -> {
                val intent = Intent(this, HelpSupport::class.java)
                startActivity(intent)
                return true
            }
            R.id.about -> {
                val intent = Intent(this, About::class.java)
                startActivity(intent)
                return true
            }
            R.id.logout -> {
                Toast.makeText(this, "Logout Selected", Toast.LENGTH_SHORT).show()
                return true
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
