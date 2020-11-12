package ru.faizovr.todo.presentation

import ru.faizovr.todo.presentation.viewholder.TaskDataView

interface TaskListContract {

    interface ViewInterface {
        fun setAddFuncToMainButton()
        fun setEditFuncToMainButton()
        fun updateList(taskList: List<TaskDataView>)
        fun setMainButtonClickable(isClickable: Boolean)
        fun setListVisibility(isVisible: Boolean)
        fun setEmptyTextMessageVisibility(isVisible: Boolean)
        fun setToDoTaskInputText(message: String)
        fun setAddTextToMainButton()
        fun setEditTextToMainButton()
        fun clearEditText()
    }

    interface PresenterInterface {
        fun init()
        fun onCheckBoxTaskClickedForPosition(position: Int)
        fun onEditTaskClickedForPosition(position: Int)
        fun listItemMoved(fromPosition: Int, toPosition: Int)
        fun listItemSwiped(position: Int)
        fun buttonAddTaskClicked(message: String)
        fun buttonEditTaskClicked(message: String)
        fun onTaskMessageInputTextChanged(message: String)
    }
}