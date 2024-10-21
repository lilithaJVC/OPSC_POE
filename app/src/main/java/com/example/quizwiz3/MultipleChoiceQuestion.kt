package com.example.quizwiz3

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "multiple_choice_questions")
data class MultipleChoiceQuestion(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "questionText") val questionText: String,
    @ColumnInfo(name = "options") val options: Map<String, String>,  // Use TypeConverter for this
    @ColumnInfo(name = "answer") val answer: String,
    @ColumnInfo(name = "category") val category: String
)

