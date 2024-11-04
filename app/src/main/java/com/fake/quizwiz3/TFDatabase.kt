package com.fake.quizwiz3

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [TrueOrFalseQuestion::class], version = 1)
@TypeConverters(Converters::class)
abstract class TFDatabase : RoomDatabase() {
    abstract fun trueOrFalseDao(): TrueOrFalseDao

    companion object{
        const val name = "tf_db"
    }

}