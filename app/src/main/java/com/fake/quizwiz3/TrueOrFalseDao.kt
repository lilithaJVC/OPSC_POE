package com.fake.quizwiz3

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface TrueOrFalseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(questions: List<TrueOrFalseQuestion>)

    @Query("SELECT * FROM true_or_false_questions WHERE category = :category")
    suspend fun getQuestionsByCategory(category: String): List<TrueOrFalseQuestion>
}