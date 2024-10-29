package com.example.quizwiz3

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.room.Room
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import java.util.UUID

class MultiChoice : AppCompatActivity() {

    // Original Views
    private lateinit var QuestionTXT: TextView
    private lateinit var answerButton1: Button
    private lateinit var answerButton2: Button
    private lateinit var answerButton3: Button
    private lateinit var nextbtn: Button
    private lateinit var backbtn: Button
    private lateinit var dashboardbtn: Button
    private lateinit var imageView2: ImageView

    // New View for displaying current player
    private lateinit var playersTextView: TextView

    // Original Variables
    private var questions: List<MultipleChoiceQuestion> = emptyList()
    private var stringQuestions: List<String> = emptyList()
    private var currentQuestionIndex = 0
    private var selectedAnswer: String? = null

    // New Variables for Player Management
    private lateinit var player1Name: String
    private lateinit var player2Name: String
    private var isPlayer1Turn = true // To alternate turns
    private var player1Score = 0 // Initialize Player 1 score
    private var player2Score = 0 // Initialize Player 2 score

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_choice)

        // Initialize Toolbar
        val toolbar: Toolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(toolbar)

        // Initialize Views
        QuestionTXT = findViewById(R.id.QuestionTXT)
        answerButton1 = findViewById(R.id.answer1)
        answerButton2 = findViewById(R.id.answer2)
        answerButton3 = findViewById(R.id.answer3)
        nextbtn = findViewById(R.id.nextbtn)
        backbtn = findViewById(R.id.backbtn)
        dashboardbtn = findViewById(R.id.dashboardbtn)
        imageView2 = findViewById(R.id.imageView2)
        playersTextView = findViewById(R.id.playerstxtview) // Initialize the new TextView

        // Retrieve player names and category from intent extras
        player1Name = intent.getStringExtra("player1Name") ?: "Player 1"
        player2Name = intent.getStringExtra("player2Name") ?: "Player 2"
        val category = intent.getStringExtra("category") ?: "Default"

        // Initialize the current player display
        updateCurrentPlayer()

        // Fetch questions based on category
        fetchQuestions(category)

        // Show the instructions dialog when the activity starts
        showInstructionsDialog()

        // Set click listeners for answer buttons
        answerButton1.setOnClickListener { handleAnswerSelection(answerButton1, answerButton1.text.toString()) }
        answerButton2.setOnClickListener { handleAnswerSelection(answerButton2, answerButton2.text.toString()) }
        answerButton3.setOnClickListener { handleAnswerSelection(answerButton3, answerButton3.text.toString()) }

        // Handle Dashboard button to go back to Dashboard
        dashboardbtn.setOnClickListener {
            val intent = Intent(this, PlayerSelection::class.java).apply {
                putExtra("player1Name", player1Name)
                putExtra("player2Name", player2Name)
            }
            startActivity(intent)
            finish() // Optionally finish this activity
        }

        // Set click listener for next button
        nextbtn.setOnClickListener { showNextQuestion(category) }

        // Set click listener for back button
        backbtn.setOnClickListener { showPreviousQuestion() }
    }

    /**
     * Updates the playersTextView with the current player's name.
     */
    private fun updateCurrentPlayer() {
        val currentPlayer = if (isPlayer1Turn) player1Name else player2Name
        playersTextView.text = "Current Player: $currentPlayer"
    }

    /**
     * Toggles the player turn between Player 1 and Player 2.
     */
    private fun togglePlayerTurn() {
        isPlayer1Turn = !isPlayer1Turn
        updateCurrentPlayer()
    }

    /**
     * Handles the selection of an answer.
     */
    private fun handleAnswerSelection(button: Button, answer: String) {
        selectedAnswer = answer
        val currentQuestion = questions[currentQuestionIndex]
        val correctAnswer = currentQuestion.answer

        if (answer.equals(correctAnswer, ignoreCase = true)) {
            button.setBackgroundColor(Color.GREEN) // Correct answer turns green
            if (isPlayer1Turn) {
                player1Score++ // Increment Player 1 score
            } else {
                player2Score++ // Increment Player 2 score
            }
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            // Set happy emoji
            imageView2.setImageResource(R.drawable.smile) // Replace with your happy emoji drawable
        } else {
            button.setBackgroundColor(Color.RED) // Incorrect answer turns red
            Toast.makeText(this, "Incorrect! The correct answer is: $correctAnswer", Toast.LENGTH_LONG).show()
            // Set sad emoji
            imageView2.setImageResource(R.drawable.sad) // Replace with your sad emoji drawable
        }

        // Disable all answer buttons to prevent multiple selections
        disableAnswerButtons()
    }

    /**
     * Disables all answer buttons after an answer has been selected.
     */
    private fun disableAnswerButtons() {
        answerButton1.isEnabled = false
        answerButton2.isEnabled = false
        answerButton3.isEnabled = false
    }

    /**
     * Enables all answer buttons for the next question.
     */
    private fun enableAnswerButtons() {
        answerButton1.isEnabled = true
        answerButton2.isEnabled = true
        answerButton3.isEnabled = true
    }

    /**
     * Resets the colors of the answer buttons to their default state.
     */
    private fun resetButtonColors() {
        // Reset all buttons to the default color
        answerButton1.setBackgroundColor(Color.parseColor("#FF1493"))
        answerButton2.setBackgroundColor(Color.parseColor("#FF1493"))
        answerButton3.setBackgroundColor(Color.parseColor("#FF1493"))

        // Re-enable the buttons for the next question
        enableAnswerButtons()

        // Reset the image view to default
        imageView2.setImageResource(R.drawable.quizstart) // Replace with your default drawable
    }

    /**
     * Fetches multiple-choice questions based on the selected category.
     */
    private fun fetchQuestions(category: String) {
        val currentLocale: Locale = Locale.getDefault()
        val languageCode: String = currentLocale.language
        var selectedLanguage = "questions"
        if (languageCode == "en") {
            selectedLanguage = "questions"
        } else if (languageCode == "af") {
            selectedLanguage = "questions-af"
        } else {
            selectedLanguage = "questions-zu"
        }
        CoroutineScope(Dispatchers.IO).launch {
            val db = Room.databaseBuilder(
                applicationContext,
                QuizDatabase::class.java, QuizDatabase.name
            ).build()
            val questionDao = db.questionDao()

            // Check if questions are available in the local Room DB
            val localQuestions = questionDao.getQuestionsByCategory("$category-$selectedLanguage")

            if (localQuestions.isNotEmpty()) {
                // If we have questions in the local DB, display them
                withContext(Dispatchers.Main) {
                    questions = localQuestions
                    if(questions.isEmpty())
                    {
                        Log.e("DatabaseEmpty", "Successfully empty")
                    }
                    else {
                        Log.e("DatabaseFull", "Successfully full")
                        displayCurrentQuestion()
                    }


                    Log.e("DatabaseSuccess", "Successfully fetch")
                }
            } else {
                // If no questions found, or if the user wants to refresh, fetch from the API
                fetchQuestionsFromApi(category)
                Log.e("DatabaseError", "Error fetch")
            }
        }
    }

    // Function to fetch from API and update Room database
    private fun fetchQuestionsFromApi(category: String) {
        val currentLocale: Locale = Locale.getDefault()
        val languageCode: String = currentLocale.language
        var selectedLanguage = "questions"
        if (languageCode == "en") {
            selectedLanguage = "questions"
        } else if (languageCode == "af") {
            selectedLanguage = "questions-af"
        } else {
            selectedLanguage = "questions-zu"
        }
        val apiService = RetrofitClient.instance.create(QuizApiService::class.java)
        apiService.getMultipleChoiceQuestions(category, selectedLanguage).enqueue(object :
            Callback<List<MultipleChoiceQuestion>> {
            override fun onResponse(call: Call<List<MultipleChoiceQuestion>>, response: Response<List<MultipleChoiceQuestion>>) {
                if (response.isSuccessful) {
                    val questionsDB = response.body()?.map {
                        MultipleChoiceQuestion(
                            id = it.id ?: generateUniqueId(), // Ensure ID is non-null
                            questionText = it.questionText,
                            options = it.options,
                            answer = it.answer,
                            category = "$category-$selectedLanguage"
                        )
                    } ?: emptyList()
                    questions = response.body() ?: emptyList()
                    stringQuestions = questions.map { it.questionText }
                    QuestionCache.cachedQuestionsMC = questions
                    displayCurrentQuestion()
                    CoroutineScope(Dispatchers.IO).launch {
                        val quizDb = Room.databaseBuilder(
                            applicationContext,
                            QuizDatabase::class.java, QuizDatabase.name
                        ).build()
                        quizDb.questionDao().insertAll(questionsDB)

                    }

                } else {
                    // Handle the error
                    QuestionTXT.text = "Failed to load questions"
                }
            }

            override fun onFailure(call: Call<List<MultipleChoiceQuestion>>, t: Throwable) {
                // Handle the failure
                QuestionTXT.text = "Please connect to the Internet to load questions"
            }
        })
    }

    fun generateUniqueId(): String {
        return UUID.randomUUID().toString()
    }

    /**
     * Displays the current question and its answer options.
     */
    private fun displayCurrentQuestion() {
        if (currentQuestionIndex < questions.size) {
            val currentQuestion = questions[currentQuestionIndex]
            QuestionTXT.text = currentQuestion.questionText

            val optionsList = currentQuestion.options.values.toList()

            answerButton1.text = optionsList.getOrNull(0) ?: "Option 1"
            answerButton2.text = optionsList.getOrNull(1) ?: "Option 2"
            answerButton3.text = optionsList.getOrNull(2) ?: "Option 3"
        } else {
            QuestionTXT.text = "No more questions available"
        }
    }

    /**
     * Shows the next question and updates the player turn.
     */
    private fun showNextQuestion(category: String) {
        if (selectedAnswer != null) {
            // Move to the next question
            currentQuestionIndex++
            if (currentQuestionIndex < questions.size) {
                displayCurrentQuestion()
                selectedAnswer = null // Reset selected answer for the next question
                resetButtonColors() // Reset the button colors for the next question
                togglePlayerTurn() // Switch to the next player
            } else {
                displayFinalScore(category) // Show score after the last question
            }
        } else {
            Toast.makeText(this, "Please select an answer first", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Shows the previous question and updates the player turn.
     */
    private fun showPreviousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex-- // Move to the previous question
            displayCurrentQuestion() // Update the UI to show the previous question
            resetButtonColors() // Reset button colors
            selectedAnswer = null // Reset selected answer
            togglePlayerTurn() // Switch to the next player
        } else {
            Toast.makeText(this, "This is the first question", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Displays the final score in the Results2 activity.
     */
    private fun displayFinalScore(category: String) {
        val intent = Intent(this, Results2::class.java).apply {
            putExtra("player1Score", player1Score)
            putExtra("player2Score", player2Score)
            putExtra("player1Name", player1Name)
            putExtra("player2Name", player2Name)
        }
        startActivity(intent)
        finish() // Optionally finish this activity
    }

    /**
     * Shows the instructions dialog.
     */
    private fun showInstructionsDialog() {

            val alertDialog = AlertDialog.Builder(this)
                .setTitle(getString(R.string.game_instructions))
                .setMessage(getString(R.string.game_instructions_multi))
                .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                    dialog.dismiss() // Dismiss the dialog when the user clicks OK
                }
                .create()

            alertDialog.show() // Display the dialog

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
