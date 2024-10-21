package com.example.quizwiz3

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuestionDao {

    @Insert
    fun insertAll(questions: List<MultipleChoiceQuestion>)

    @Query("SELECT * FROM multiple_choice_questions WHERE category = :category")
    suspend fun getQuestionsByCategory(category: String): List<MultipleChoiceQuestion>
}