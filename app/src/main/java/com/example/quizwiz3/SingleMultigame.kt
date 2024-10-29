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
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.UUID


class SingleMultigame : AppCompatActivity() {

    //code attribution
    //this code was take n from W3Schools :
    //link:https://www.w3schools.com/java/java_variables_multiple.asp

    private lateinit var QuestionTXT: TextView
    private lateinit var answerButton1: Button
    private lateinit var answerButton2: Button
    private lateinit var answerButton3: Button
    private lateinit var nextbtn: Button
    private lateinit var backbtn: Button
    private lateinit var resultTextView: TextView
    private lateinit var dashboardbtn: Button



    private var questions: List<MultipleChoiceQuestion> = emptyList()
    private var stringQuestions: List<String> = emptyList()
    private var currentQuestionIndex = 0
    private var selectedAnswer: String? = null
    private var score = 0 // Initialize the score




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_single_multigame)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val toolbar: Toolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(toolbar)

        QuestionTXT = findViewById(R.id.QuestionTXT)
        answerButton1 = findViewById(R.id.answer1)
        answerButton2 = findViewById(R.id.answer2)
        answerButton3 = findViewById(R.id.answer3)
        nextbtn = findViewById(R.id.nextbtn)
        backbtn = findViewById(R.id.backbtn)
       // resultTextView = findViewById(R.id.resultTextView)

        val category = intent.getStringExtra("category") ?: "Default"
        fetchQuestions(category)



        // Show the alert dialog when the activity starts
        showInstructionsDialog()

        // Set click listeners for answer buttons
        //code attribution
        //this code was taken from  stack overflow
        //link:https://stackoverflow.com/questions/20156733/how-to-add-button-click-event-in-android-studio
        answerButton1.setOnClickListener { handleAnswerSelection(answerButton1, answerButton1.text.toString()) }
        answerButton2.setOnClickListener { handleAnswerSelection(answerButton2, answerButton2.text.toString()) }
        answerButton3.setOnClickListener { handleAnswerSelection(answerButton3, answerButton3.text.toString()) }

        val dashboardbtn: Button = findViewById(R.id.dashboardbtn)

        // Handle Back button to go to PlayerSelection
        dashboardbtn.setOnClickListener {
            val intent = Intent(this, PlayerSelection::class.java)
            startActivity(intent)
        }

        // Set click listener for next button
        nextbtn.setOnClickListener { showNextQuestion(category) }

        // Set click listener for back button
        backbtn.setOnClickListener { showPreviousQuestion() }
    }
    //code attribution
    //this code was taken from youtube
    //link:https://www.youtube.com/results?search_query=linking+a+button+to+aclass+on+adnroid+studio+

    private fun showPreviousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex-- // Move to the previous question
            displayCurrentQuestion() // Update the UI to show the previous question
            selectedAnswer = null // Reset selected answer for the previous question
            resetButtonColors() // Reset button colors when going back
        } else {
            Toast.makeText(this, "This is the first question", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showInstructionsDialog() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.game_instructions))
            .setMessage(getString(R.string.game_instructions2))
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss() // Dismiss the dialog when the user clicks OK
            }
            .create()

        alertDialog.show() // Display the dialog
    }
    //code attribution
    //this code was taken from  stack overflow
    //link:https://stackoverflow.com/questions/20156733/how-to-add-button-click-event-in-android-studio
    private fun handleAnswerSelection(button: Button, answer: String) {
        selectedAnswer = answer
        val currentQuestion = questions[currentQuestionIndex]
        val imageView2: ImageView = findViewById(R.id.imageView2)

        if (answer == currentQuestion.answer) {
            button.setBackgroundColor(Color.GREEN) // Correct answer turns green
            score++
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            // Set happy emoji
            imageView2.setImageResource(R.drawable.smile) // Replace with your happy emoji drawable
        } else {
            button.setBackgroundColor(Color.RED) // Incorrect answer turns red
            Toast.makeText(this, "Incorrect! The correct answer is: ${currentQuestion.answer}", Toast.LENGTH_LONG).show()
            // Set sad emoji
            imageView2.setImageResource(R.drawable.sad) // Replace with your sad emoji drawable
        }
    }


    private fun showNextQuestion(category: String) {
        if (selectedAnswer != null) {
            // Move to the next question
            currentQuestionIndex++
            if (currentQuestionIndex < questions.size) {
                displayCurrentQuestion()
                selectedAnswer = null // Reset selected answer for the next question
                resetButtonColors() // Reset the button colors for the next question
            } else {
                displayFinalScore(category) // Show score after the last question
            }
        } else {
            Toast.makeText(this, "Please select an answer first", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetButtonColors() {
        // Reset all buttons to the default color
        answerButton1.setBackgroundColor(Color.parseColor("#FF1493"))
        answerButton2.setBackgroundColor(Color.parseColor("#FF1493"))
        answerButton3.setBackgroundColor(Color.parseColor("#FF1493"))
    }
    //code attribution
    //this code was taken from youtube
    //link:https://www.youtube.com/results?search_query=linking+a+button+to+aclass+on+adnroid+studio+

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
                        if (questions.isEmpty()) {
                            Log.e("DatabaseEmpty", "Successfully empty")
                        } else {
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
    //code attribution
    //this code was taken from  stack overflow
    //link:https://stackoverflow.com/questions/20156733/how-to-add-button-click-event-in-android-studio
    private fun displayCurrentQuestion() {
        if (questions.isNotEmpty() && currentQuestionIndex < questions.size) {
            val currentQuestion = questions[currentQuestionIndex]
            QuestionTXT.text = currentQuestion.questionText

            val optionsList = currentQuestion.options.values.toList()

            answerButton1.text = optionsList.getOrNull(0) ?: ""
            answerButton2.text = optionsList.getOrNull(1) ?: ""
            answerButton3.text = optionsList.getOrNull(2) ?: ""
        } else {
            QuestionTXT.text = "No more questions available"
        }
    }

    private fun displayFinalScore(category: String) {
        // resultTextView.text = "Quiz Finished! Your score: $score/${questions.size}"
        val intent = Intent(this, Results::class.java)
        intent.putExtra("category", category)
        intent.putExtra("type", "MultipleChoice")
        intent.putExtra("score", "$score")
        intent.putExtra("totalQuestions", "${questions.size}")
        startActivity(intent)
        nextbtn.isEnabled = false // Disable the next button if no more questions
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
