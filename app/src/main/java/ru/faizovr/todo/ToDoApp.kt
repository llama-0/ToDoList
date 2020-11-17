package ru.faizovr.todo

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import ru.faizovr.todo.domain.model.Model

class ToDoApplication : Application() {

    lateinit var model: Model
        private set

    override fun onCreate() {
        super.onCreate()
        setupModel()
    }

    private fun setupModel() {
        val sharedPreferences: SharedPreferences = getSharedPreferences(PREFERENCES, 0)
        Log.d("TODOAPP", "setupModel: ")
        model = Model(sharedPreferences)
    }

    companion object {
        private const val PREFERENCES = "app_preferences"
    }
}