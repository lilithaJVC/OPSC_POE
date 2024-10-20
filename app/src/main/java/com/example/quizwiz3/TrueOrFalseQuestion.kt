package com.example.quizwiz3

data class TrueOrFalseQuestion(
    val questionText: String,
    val correctAnswer: Boolean,
    val incorrectAnswer: Boolean // Or a String for the incorrect answer
)
