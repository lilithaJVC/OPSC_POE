package com.example.quizwiz3

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface QuizApiService {
    //code attribution
    //this code was taken from youtube
    //link:https://youtu.be/lz5lPAdA3fQ?si=JhNTuySEabDPHz3k
    @GET("api/TrueOrFalseQuiz/get/{category}")
    fun getQuestions(@Path("category") category: String): Call<List<TrueOrFalseQuestion>>
    //code attribution
    //this code was taken from youtube
    //link:https://youtu.be/lz5lPAdA3fQ?si=JhNTuySEabDPHz3k
    @GET("api/Quiz/get/{category}")
    fun getMultipleChoiceQuestions(@Path("category") category: String): Call<List<MultipleChoiceQuestion>>

    @POST("api/PostRegister")
    fun postUserDetails(@Body user: UserDetails): Call<Void>
}

// Data class to represent the user details
data class UserDetails(
    val name: String,
    val email: String,
    val firebaseUUID: String
)
