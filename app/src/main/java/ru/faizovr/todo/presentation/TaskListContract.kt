package ru.faizovr.todo.presentation

import ru.faizovr.todo.data.Model
import ru.faizovr.todo.data.Task

interface TaskListContract {

    interface ViewInterface {
        fun displayList(taskList: List<Task>)
        fun displayNoList()
    }

    interface PresenterInterface {
        fun init()
        fun addTaskToList(message: String)
        fun getMyModel(): Model
        fun getList(): List<Task>
    }
}