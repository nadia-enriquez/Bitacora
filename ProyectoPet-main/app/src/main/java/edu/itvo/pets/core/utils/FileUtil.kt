package edu.itvo.pets.core.utils

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader

fun loadJsonFromAssets(context: Context, fileName: String): List<String> {
    return try {
        val inputStream = context.assets.open(fileName)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val json = bufferedReader.use { it.readText() }
        val listType = object : TypeToken<List<String>>() {}.type
        Gson().fromJson(json, listType)
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }
}