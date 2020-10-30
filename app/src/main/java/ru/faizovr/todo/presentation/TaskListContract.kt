package ru.faizovr.todo.presentation

import ru.faizovr.todo.data.Task

interface TaskListContract {

    interface ViewInterface {
        fun displayList(taskList: List<Task>)
        fun displayNoList()
        fun changeButtonClickable(isClickable: Boolean)
        fun clearEditText()
    }

    interface PresenterInterface {
        fun init()
        fun listItemSwapped(position: Int)
        fun buttonAddTaskClicked(message: String)
        fun textChanged(string: String)
        fun getList(): List<Task>
    }
}