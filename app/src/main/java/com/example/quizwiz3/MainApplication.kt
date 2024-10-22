package com.example.quizwiz3

import android.app.Application
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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