package ru.faizovr.todo.presentation

import ru.faizovr.todo.data.Task

interface TaskListContract {

    interface ViewInterface {
        fun displayList(taskList: List<Task>)
        fun displayNoList()
        fun changeButtonClickable(isStringEmpty: Boolean)
        fun clearEditText()
    }

    interface PresenterInterface {
        fun init()
        fun buttonAddTaskClicked(message: String)
        fun textChanged(string: String)
        fun getList(): List<Task>
    }
}