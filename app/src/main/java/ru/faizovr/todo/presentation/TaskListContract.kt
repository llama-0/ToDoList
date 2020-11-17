package ru.faizovr.todo.presentation

import ru.faizovr.todo.presentation.viewholder.TaskDataView

interface TaskListContract {

    interface ViewInterface {
        fun setFuncToMainButton()
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
        fun onMainButtonClicked(message: String)
        fun onTaskMessageInputTextChanged(message: String)
        fun onSaveInstanceState()
    }
}