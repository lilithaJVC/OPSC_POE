package com.example.quizwiz3

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

//___________code attribution___________
//The following code was taken from Easy Tuto Room Database in Android Studio
//Author: Easy Tuto
//Link: https://www.youtube.com/watch?v=sWOmlDvz_3U
@Database(entities = [TrueOrFalseQuestion::class], version = 1)
@TypeConverters(Converters::class)
abstract class TFDatabase : RoomDatabase() {
    abstract fun trueOrFalseDao(): TrueOrFalseDao

    companion object{
        const val name = "tf_db"
    }

}
//___________end___________