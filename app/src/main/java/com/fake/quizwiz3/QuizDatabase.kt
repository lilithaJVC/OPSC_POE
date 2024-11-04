package com.fake.quizwiz3

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MultipleChoiceQuestion::class], version = 1)
@TypeConverters(Converters::class)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao

    companion object{
        const val name = "quiz_db"
    }

}

