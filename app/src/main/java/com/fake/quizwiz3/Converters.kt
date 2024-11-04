package com.fake.quizwiz3

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromMap(value: Map<String, String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toMap(value: String): Map<String, String> {
        return Gson().fromJson(value, object : TypeToken<Map<String, String>>() {}.type)
    }
}
