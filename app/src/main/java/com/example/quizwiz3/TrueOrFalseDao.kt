package com.example.quizwiz3

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

//___________code attribution___________
//The following code was taken from Easy Tuto Room Database in Android Studio
//Author: Easy Tuto
//Link: https://www.youtube.com/watch?v=sWOmlDvz_3U
@Dao
interface TrueOrFalseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(questions: List<TrueOrFalseQuestion>)

    @Query("SELECT * FROM true_or_false_questions WHERE category = :category")
    suspend fun getQuestionsByCategory(category: String): List<TrueOrFalseQuestion>
}
//___________end___________