package com.example.quizwiz3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.SignInButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Results : AppCompatActivity() {
    private var cachedQuestionsMC: List<MultipleChoiceQuestion> = emptyList()
    private var cachedQuestionsTF: List<TrueOrFalseQuestion> = emptyList()

    private lateinit var rvCategories: RecyclerView
    private lateinit var QuestionsAdapter: QuestionsAdapter
    private lateinit var QuestionsAdapterTrueOrFalse: QuestionsAdapterTrueOrFalse
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_results)

        cachedQuestionsMC = QuestionCache.cachedQuestionsMC
        cachedQuestionsTF = QuestionCache.cachedQuestionsTF

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Show the alert dialog when the activity starts
        showInstructionsDialog()

        rvCategories = findViewById<RecyclerView>(R.id.rvCategories)
        resultTextView = findViewById(R.id.resultTextView)
        val category = intent.getStringExtra("category") ?: "Default"
        val type = intent.getStringExtra("type") ?: "Default"
        val score = intent.getStringExtra("score") ?: "Default"
        val totalQuestions = intent.getStringExtra("totalQuestions") ?: "Default"

        resultTextView.text = "Quiz Finished! Your score: $score/${totalQuestions}"
        if(type == "MultipleChoice")
        {
            fetchQuestionsMultipleChoice(cachedQuestionsMC)

        } else
        {
            fetchQuestionsTrueOrFalse(cachedQuestionsTF)

        }
    }
    private fun showInstructionsDialog() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.results_title))
            .setMessage(getString(R.string.results))
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss() // Dismiss the dialog when the user clicks OK
            }
            .create()

        alertDialog.show() // Display the dialog

    }
//___________code attribution___________
//The following code was taken from Medium
//Author: Ranjan Mishra
//Link: https://ranjanmishramed.medium.com/how-to-create-custom-adapter-for-recycler-view-android-kotlin-f8da7fc6260e
    private fun displayQuestionsMC(questions: List<MultipleChoiceQuestion>) {
        QuestionsAdapter = QuestionsAdapter(questions) { selectedQuestion ->

            // Convert the options from a map to a list
            val optionsList = selectedQuestion.options.values.toList()
            // Pass both question and options to the next activity
            val intent = Intent(this, ChatGPTResult::class.java)
            intent.putExtra("selectedQuestion", selectedQuestion.questionText)
            intent.putStringArrayListExtra("optionsList", ArrayList(optionsList)) // Pass the options list
            startActivity(intent)
        }

        rvCategories.layoutManager = LinearLayoutManager(this)
        rvCategories.adapter = QuestionsAdapter
    }
//___________end___________
    private fun displayQuestionsTF(questions: List<TrueOrFalseQuestion>) {
        QuestionsAdapterTrueOrFalse = QuestionsAdapterTrueOrFalse(questions) { selectedQuestion ->

            // Pass both question and options to the next activity
            val intent = Intent(this, ChatGPTResult::class.java)
            intent.putExtra("selectedQuestion", selectedQuestion.questionText)
            startActivity(intent)
        }

        rvCategories.layoutManager = LinearLayoutManager(this)
        rvCategories.adapter = QuestionsAdapterTrueOrFalse
    }



    private fun fetchQuestionsMultipleChoice(cachedQuestionMC2: List<MultipleChoiceQuestion>) {
     displayQuestionsMC(cachedQuestionMC2)
    }

    private fun fetchQuestionsTrueOrFalse(cachedQuestionsTF2: List<TrueOrFalseQuestion>) {
        displayQuestionsTF(cachedQuestionsTF2)
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