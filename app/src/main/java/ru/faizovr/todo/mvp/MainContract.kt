package ru.faizovr.todo.mvp

import ru.faizovr.todo.model.Task

interface MainContract {

    interface ViewInterface {
        fun displayList()
        fun displayNoList()
    }

    interface PresenterInterface {
        fun getMyList(): List<Task>
        fun showList()
        fun addTaskToList(message: String)
    }

//    interface Model {
//        fun loadMessage()
//    }
}