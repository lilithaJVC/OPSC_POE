package com.fake.quizwiz3

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuestionDao {
//___________code attribution___________
//The following code was taken from Easy Tuto Room Database in Android Studio
//Author: Easy Tuto
//Link: https://www.youtube.com/watch?v=sWOmlDvz_3U

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(questions: List<MultipleChoiceQuestion>)

    @Query("SELECT * FROM multiple_choice_questions WHERE category = :category")
    suspend fun getQuestionsByCategory(category: String): List<MultipleChoiceQuestion>
}

//___________end___________