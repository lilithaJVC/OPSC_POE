package com.example.quizwiz3

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class trueorfalseSingleMode : AppCompatActivity() {


    //code attribution
    //this code was taken from youtube
    //link:https://www.youtube.com/results?search_query=linking+a+button+to+aclass+on+adnroid+studio+


    private lateinit var Backbtn2: Button
    private lateinit var questionTXT2: TextView
    private lateinit var trueBtn: Button
    private lateinit var falseBtn: Button
    private lateinit var nextBtn: Button
    private lateinit var resultTextView: TextView
    private lateinit var dashboardbtn: Button

    private lateinit var imageView: ImageView


    private var questions: List<TrueOrFalseQuestion> = emptyList()
    private var currentQuestionIndex = 0
    private var score = 0
    private var selectedAnswer: Boolean? = null // Store the selected answer

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_trueorfalse_single_mode)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Initialize views
        questionTXT2 = findViewById(R.id.questionTXT2)
        trueBtn = findViewById(R.id.truebtn)
        falseBtn = findViewById(R.id.falsebtn)
        nextBtn = findViewById(R.id.nextbtn)
        resultTextView = findViewById(R.id.resultTextView)
        imageView = findViewById(R.id.imageView)


        val toolbar: Toolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(toolbar)
        // Get the category passed from Dashboard
        val category = intent.getStringExtra("category") ?: "Default"

        // Show the alert dialog when the activity starts
        showInstructionsDialog()

        // Fetch questions for the category
       // fetchQuestions(category)

        Backbtn2 = findViewById(R.id.Backbtn2)
        Backbtn2.setOnClickListener {
            showPreviousQuestion()
        }

        nextBtn.setOnClickListener {
            showNextQuestion()
        }

        trueBtn.setOnClickListener {
            handleAnswerSelection(true)
        }

        falseBtn.setOnClickListener {
            handleAnswerSelection(false)
        }

        dashboardbtn = findViewById(R.id.dashboardbtn)
        // Handle Back button to go to Dashboard
        dashboardbtn.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }
    }

    private fun showInstructionsDialog() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Instructions")
            .setMessage("Please click True or False to select your answer and click Next to proceed to the next question.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss() // Dismiss the dialog when the user clicks OK
            }
            .create()

        alertDialog.show() // Display the dialog
    }

    private fun fetchQuestions(category: String) {
        val apiService = RetrofitClient.instance.create(QuizApiService::class.java)
        val call = apiService.getQuestions(category,  "questions-af")

        call.enqueue(object : Callback<List<TrueOrFalseQuestion>> {
            override fun onResponse(call: Call<List<TrueOrFalseQuestion>>, response: Response<List<TrueOrFalseQuestion>>) {
                if (response.isSuccessful) {
                    questions = response.body() ?: emptyList()
                    if (questions.isNotEmpty()) {
                        QuestionCache.cachedQuestionsTF = questions
                        displayQuestion()
                        Toast.makeText(this@trueorfalseSingleMode, "Questions fetched successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@trueorfalseSingleMode, "No questions found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("QuizWiz", "Failed to fetch questions: ${response.errorBody()?.string()}")
                    Toast.makeText(this@trueorfalseSingleMode, "Failed to fetch questions: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<TrueOrFalseQuestion>>, t: Throwable) {
                Log.e("QuizWiz", "Error fetching questions", t)
                Toast.makeText(this@trueorfalseSingleMode, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayQuestion() {
        if (currentQuestionIndex < questions.size) {
            val question = questions[currentQuestionIndex]
            questionTXT2.text = question.questionText

            // Reset button colors and states
            resetButtonColors()
            trueBtn.isEnabled = true
            falseBtn.isEnabled = true
            selectedAnswer = null // Reset selected answer
        } else {
            displayScore() // If no more questions, show score
        }
    }

    private fun handleAnswerSelection(answer: Boolean) {
        selectedAnswer = answer
        val correctAnswer = questions[currentQuestionIndex].correctAnswer

        // Check the selected answer
        if (selectedAnswer == correctAnswer) {
            score++ // Increment score if the answer is correct
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()

            // Change the button color to green
            if (answer) {
                trueBtn.setBackgroundColor(resources.getColor(android.R.color.holo_green_light))
            } else {
                falseBtn.setBackgroundColor(resources.getColor(android.R.color.holo_green_light))
            }

            // Set happy emoji
            //code attribution
            //this code was take from youtube
            // link:https://www.youtube.com/results?search_query=linking+a+button+to+aclass+on+adnroid+studio+
            imageView.setImageResource(R.drawable.smile) // Replace with your happy emoji drawable
        } else {
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()

            // Change the button color to red
            if (answer) {
                trueBtn.setBackgroundColor(resources.getColor(android.R.color.holo_red_light))
            } else {
                falseBtn.setBackgroundColor(resources.getColor(android.R.color.holo_red_light))
            }

            // Set sad emoji
            imageView.setImageResource(R.drawable.sad) // Replace with your sad emoji drawable
        }

        // Make the ImageView visible
        imageView.visibility = View.VISIBLE

        // Disable the answer buttons after selection
        trueBtn.isEnabled = false
        falseBtn.isEnabled = false
    }


    private fun showNextQuestion() {
        if (selectedAnswer != null) {
            // Move to the next question
            currentQuestionIndex++
            displayQuestion() // Display the new question
        } else {
            Toast.makeText(this, "Please select an answer first", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showPreviousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex-- // Move to the previous question
            displayQuestion() // Update the UI to show the previous question
        } else {
            Toast.makeText(this, "This is the first question", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetButtonColors() {
        // Reset button colors to default
        trueBtn.setBackgroundColor(resources.getColor(android.R.color.transparent))
        falseBtn.setBackgroundColor(resources.getColor(android.R.color.transparent))
    }

    private fun displayScore() {
        // resultTextView.text = "Quiz Finished! Your score: $score/${questions.size}"
        val intent = Intent(this, Results::class.java)
        intent.putExtra("score", "$score")
        intent.putExtra("totalQuestions", "${questions.size}")
        startActivity(intent)
        nextBtn.isEnabled = false // Disable the next button
        trueBtn.isEnabled = false // Disable the true button
        falseBtn.isEnabled = false // Disable the false button
    }

    // Inflate the menu
    //code attribution
    //this code was taken from  stack overflow
    //link:https://stackoverflow.com/questions/20156733/how-to-add-button-click-event-in-android-studio
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
                val intent = Intent(this, Dashboard::class.java)
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
                val intent = Intent(this, Logout::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}

