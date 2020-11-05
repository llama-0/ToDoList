package ru.faizovr.todo.presentation

import ru.faizovr.todo.data.Task

interface TaskListContract {

    interface ViewInterface {
        fun setupAddButton()
        fun setupEditButton()
        fun updateList(taskList: List<Task>)
        fun changeButtonClickable(isClickable: Boolean)
        fun changeListVisibility(isVisible: Boolean)
        fun changeEmptyTextMessageVisibility(isVisible: Boolean)
        fun changeEditTextText(string: String)
        fun changeAddButtonText(string: String)
        fun setAddTextToButton()
        fun setEditTextToButton()
        fun clearEditText()
    }

    interface PresenterInterface {
        fun init()
        fun buttonListEditTaskClicked(position: Int)
        fun listItemMoved(fromPosition: Int, toPosition: Int)
        fun listItemSwiped(position: Int)
        fun buttonAddTaskClicked(string: String)
        fun editTextTextChanged(string: String)
        fun buttonEditTaskClicked(string: String)
    }
}