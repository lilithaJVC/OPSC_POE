package com.fake.quizwiz3

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth

class Profile : AppCompatActivity() {

    private lateinit var backButton: Button
    private lateinit var userNameTextView: TextView
    private lateinit var userEmailTextView: TextView
    private lateinit var icon: ImageView
    private var selectedIcon: Int = 0 // Declare selectedIcon variable
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        val toolbar: Toolbar = findViewById(R.id.toolbarProfile)
        setSupportActionBar(toolbar)
        auth = FirebaseAuth.getInstance()

       // changeProfileButton = findViewById(R.id.btnChangeProfile)
        userNameTextView = findViewById(R.id.Name)
        userEmailTextView = findViewById(R.id.userEmail)
        backButton = findViewById(R.id.btnBack)
        //icon = findViewById(R.id.iconImageView)

        // Set user name and email from Firebase Auth
        val currentUser = auth.currentUser
        if (currentUser != null) {
            userEmailTextView.text = currentUser.email // Display user's email
            userNameTextView.text = currentUser.displayName // Display user's name (if set)
          //  icon.setImageResource(selectedIcon)
        }

        backButton.setOnClickListener {
            val intent: Intent = Intent(this@Profile, Dashboard::class.java)
            startActivity(intent)
        }

        // Set click listener for the Change Profile Picture button
//        changeProfileButton.setOnClickListener {
//            showIconSelectionPopup()
//        }
    }
//
//    private fun showIconSelectionPopup() {
//        // Inflate the custom layout
//        val popupView = layoutInflater.inflate(R.layout.popup_icon_selection, null)
//
//        // Set up the RecyclerView in the popup
//        val iconRecyclerView = popupView.findViewById<RecyclerView>(R.id.iconRecyclerView)
//        iconRecyclerView.layoutManager = GridLayoutManager(this, 3)
//
//        // Example icon data (replace with your actual icons)
//        val iconList = listOf(
//            R.drawable.icon1, R.drawable.icon2, R.drawable.icon3,
//            R.drawable.icon4, R.drawable.icon5, R.drawable.icon6,
//            R.drawable.icon7, R.drawable.icon8, R.drawable.icon9,
//            R.drawable.icon10
//        )
//
//        // Create the AlertDialog
//        val dialog = AlertDialog.Builder(this)
//            .setView(popupView)
//            .setCancelable(true)
//            .create()
//
//        // Adapter with click listener
//        val iconAdapter = IconAdapter(iconList) { icon ->
//            // Handle icon selection
//            selectedIcon = icon // Update selected icon
//            Toast.makeText(this, "Icon Selected!", Toast.LENGTH_SHORT).show()
//
//
//            // Update Firestore with the new profile icon
//            updateProfileIcon(selectedIcon)
//
//            // Dismiss the popup dialog
//            dialog.dismiss()
//        }
//
//        iconRecyclerView.adapter = iconAdapter
//
//        // Show the dialog
//        dialog.show()
//    }
//
//    private fun updateProfileIcon(iconResId: Int) {
//        // Get the current user
//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            // Create a map to hold the data to update
//            val userUpdates = hashMapOf(
//                "profileIcon" to iconResId, // You might need to convert the resource ID to a URL if using images
//                "email" to currentUser.email,
//                "name" to currentUser.displayName
//            )
//
//            // Update Firestore under a collection named "users" (or your relevant collection)
////            firestore.collection("user")
////                .document(currentUser.uid) // Use user's UID as the document ID
////                .set(userUpdates)
////                .addOnSuccessListener {
////                    Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
////                }
////                .addOnFailureListener {
////                    Toast.makeText(this, "Error updating profile: ", Toast.LENGTH_SHORT).show()
////                }
//        } else {
//            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
//        }
//    }

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
