package com.example.quizwiz3

import android.content.Context
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
//___________code attribution___________
//The following code was taken from Easy Tuto Room Database in Android Studio
//Author: Easy Tuto
//Link: https://www.youtube.com/watch?v=sWOmlDvz_3U
@Database(entities = [MultipleChoiceQuestion::class], version = 1)
@TypeConverters(Converters::class)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao

    companion object{
        const val name = "quiz_db"
    }

}
//___________end___________
