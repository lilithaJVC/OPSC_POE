package com.fake.quizwiz3

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import com.google.androidgamesdk.gametextinput.Settings

class GameSettings : AppCompatActivity() {

    private lateinit var btnBackSettings: Button
    private lateinit var btnProfileSettings: Button

    // MediaPlayer to handle background music
    private var mediaPlayer: MediaPlayer? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_settings)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Back to Settings Menu Button
        btnBackSettings = findViewById(R.id.btnBackSettings)
        btnBackSettings.setOnClickListener {
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }

        // Profile Settings Button
        btnProfileSettings = findViewById(R.id.btnProfileSettings)
        btnProfileSettings.setOnClickListener {
            val intent = Intent(this, GameSettings::class.java)
            startActivity(intent)
        }

        // Access SharedPreferences to save the state of the switches
        val preferences: SharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preferences.edit()

        // 1. Dark/Light Mode Toggle
        val modeSwitch = findViewById<Switch>(R.id.ModeSwitch)
        modeSwitch.isChecked = preferences.getBoolean("darkMode", false)
        modeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("darkMode", true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean("darkMode", false)
            }
            editor.apply()
        }

        // 2. Notifications Toggle
        val notificationsSwitch = findViewById<Switch>(R.id.NotificationsSwitch)
        notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                enableNotifications()
                editor.putBoolean("notificationsEnabled", true)
            } else {
                disableNotifications()
                editor.putBoolean("notificationsEnabled", false)
            }
            editor.apply()
        }

        // 3. Sound/Music Toggle
        val soundSwitch = findViewById<Switch>(R.id.SoundMusicSwitch)
        soundSwitch.isChecked = preferences.getBoolean("soundEnabled", false)
        soundSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                enableSound()
                editor.putBoolean("soundEnabled", true)
            } else {
                disableSound()
                editor.putBoolean("soundEnabled", false)
            }
            editor.apply()
        }

        // Automatically play background music if sound is enabled
        if (preferences.getBoolean("soundEnabled", false)) {
            enableSound()
        }
    }

    // Enable Notifications
    private fun enableNotifications() {
        // Notification logic here
    }

    // Disable Notifications
    private fun disableNotifications() {
        // Notification logic here
    }

    // Enable Sound/Music (Play background music)
    private fun enableSound() {
        if (mediaPlayer == null) {
            // Initialize mediaPlayer with the background music located in res/raw folder
            mediaPlayer = MediaPlayer.create(this, R.raw.background_music) // Ensure you have added the file in res/raw
            mediaPlayer?.isLooping = true // Loop the music
        }
        mediaPlayer?.start() // Start playing the background music
    }

    // Disable Sound/Music (Pause background music)
    private fun disableSound() {
        mediaPlayer?.pause() // Pause the music
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release mediaPlayer resources when activity is destroyed
        mediaPlayer?.release()
        mediaPlayer = null
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
                startActivity(Intent(this, com.fake.quizwiz3.Settings::class.java))
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
                Toast.makeText(this, "Logged Out acti", Toast.LENGTH_SHORT).show()
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
