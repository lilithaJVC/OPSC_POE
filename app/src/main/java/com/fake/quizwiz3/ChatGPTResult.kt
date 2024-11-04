package com.fake.quizwiz3

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class ChatGPTResult : AppCompatActivity() {

    private lateinit var textViewAnswer: TextView
    private lateinit var textViewQuestion: TextView

    private val stringAPIKey = "AIzaSyAC0kKEZg_UjPUcKIA93qnEoPSdudgvalw"

    @SuppressLint("SuspiciousIndentation", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat_gptresult)
        val question = intent.getStringExtra("selectedQuestion") ?: "Default"
        val optionsList = intent.getStringArrayListExtra("optionsList")

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)



        textViewAnswer = findViewById(R.id.tvQResult)
        textViewQuestion = findViewById(R.id.tvQuestion)
        val options = optionsList?.joinToString(", ")
        val fullQuestion = question + options

        if(optionsList == null)
        {
 //___________code attribution___________
//The following code was taken from Medium
//Author: Bhoomi Vagasiya
//Link: https://medium.com/@bhoomigadhiya/integrating-googles-gemini-into-the-android-app-520508975c2e
            lifecycleScope.launch {
                try {
                    val generativeModel = GenerativeModel(
                        modelName = "gemini-1.5-flash",
                        apiKey = stringAPIKey
                    )
                    val prompt = question
                    val response = generativeModel.generateContent(prompt)
                    print(response.text)
                    textViewAnswer.text = "\n " + response.text
                    textViewQuestion.text = "\n " + prompt
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this@ChatGPTResult, "Error generating content", Toast.LENGTH_SHORT).show()
                }
            }
        }
        else{
            lifecycleScope.launch {
                try {
                    val generativeModel = GenerativeModel(
                        modelName = "gemini-1.5-flash",
                        apiKey = stringAPIKey
                    )
                    val prompt = fullQuestion
                    val response = generativeModel.generateContent(prompt)
                    print(response.text)
                    textViewAnswer.text = "\n " + response.text
                    textViewQuestion.text = "\n " + fullQuestion
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this@ChatGPTResult, "Error generating content", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    //___________end___________

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