package ru.faizovr.todo.presentation

import ru.faizovr.todo.data.Task

interface TaskListContract {

    interface ViewInterface {
        fun displayList(taskList: List<Task>)
        fun displayNoList()
    }

    interface PresenterInterface {
        fun init()
        fun addTaskToList(message: String)
        fun getList(): List<Task>
    }
}