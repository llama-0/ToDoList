package ru.faizovr.todo

import android.app.Application
import ru.faizovr.todo.data.Model

class ToDoApplication: Application() {

    var model: Model? = null

    override fun onCreate() {
        super.onCreate()
        setupModel()
    }

    private fun setupModel() {
        model = Model(mutableListOf())
    }
}