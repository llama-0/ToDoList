package ru.faizovr.todo

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import ru.faizovr.todo.data.RepositoryImplementation
import ru.faizovr.todo.domain.model.Model

class ToDoApplication : Application() {

    lateinit var model: Model
        private set

    override fun onCreate() {
        super.onCreate()
        setupModel()
    }

    private fun setupModel() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        val repository = RepositoryImplementation(sharedPreferences)
        model = Model(repository)
    }

    companion object {
        private const val PREFERENCES = "app_preferences"
    }
}