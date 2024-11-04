package com.fake.quizwiz3

import android.app.Application
import androidx.room.Room

class MainApplication : Application() {
companion object{
    @Volatile
    lateinit var quizDb: QuizDatabase
}
    override fun onCreate(){
        super.onCreate()
        quizDb = Room.databaseBuilder(
        applicationContext,
        QuizDatabase::class.java, QuizDatabase.name
        ).build()

    }
}