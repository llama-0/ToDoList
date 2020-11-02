package ru.faizovr.todo.presentation

import ru.faizovr.todo.data.Task

interface TaskListContract {

    interface ViewInterface {
        fun updateList(taskList: List<Task>)
        fun changeButtonClickable(isClickable: Boolean)
        fun changeListVisibility(isVisible: Boolean)
        fun changeEmptyTextMessageVisibility(isVisible: Boolean)
        fun clearEditText()
    }

    interface PresenterInterface {
        fun init()
        fun listItemSwipped(position: Int)
        fun buttonAddTaskClicked(message: String)
        fun textChanged(string: String)
    }
}