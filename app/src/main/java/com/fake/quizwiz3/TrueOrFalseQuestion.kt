package com.fake.quizwiz3

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "true_or_false_questions")
data class TrueOrFalseQuestion(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "questionText") val questionText: String,
    @ColumnInfo(name = "correctAnswer")val correctAnswer: Boolean,
    @ColumnInfo(name = "incorrectAnswer")val incorrectAnswer: Boolean,
    @ColumnInfo(name = "category") val category: String
)
