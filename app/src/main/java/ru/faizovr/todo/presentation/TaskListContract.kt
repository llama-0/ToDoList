package ru.faizovr.todo.presentation

import ru.faizovr.todo.data.Task

interface TaskListContract {

    interface ViewInterface {
        fun updateList(taskList: List<Task>)
        fun changeButtonClickable(isClickable: Boolean)
        fun changeListVisibility(isVisible: Boolean)
        fun changeEmptyTextMessageVisibility(isVisible: Boolean)
        fun changeEditTextText(message: String)
        fun changeAddButtonText(message: String)
        fun clearEditText()
    }

    interface PresenterInterface {
        fun init()
        fun listItemSwiped(task: Task)
        fun buttonAddTaskClicked(message: String)
        fun buttonEditTaskClicked(task: Task, saveString: String)
        fun editTextTextChanged(string: String)
    }
}