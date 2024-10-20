package com.example.quizwiz3

data class MultipleChoiceQuestion(
    val questionText: String,
    val options: Map<String, String>,  // Change to Map<String, String>
    val answer: String
)

