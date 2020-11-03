package ru.faizovr.todo.presentation.presenter

import ru.faizovr.todo.data.Model
import ru.faizovr.todo.data.Task
import ru.faizovr.todo.presentation.TaskListContract

class TaskListPresenter (private val viewInterface: TaskListContract.ViewInterface, private val model: Model)
    : TaskListContract.PresenterInterface {

    private var editTextString: String = ""

    override fun init() {
        showContent()
    }

    private fun deleteTask(task: Task) {
        if (model.isContain(task)) {
            model.deleteTask(task)
        }
    }

    override fun listItemMoved(task: Task, toPosition: Int) {
        model.swapTask(task, toPosition)
        showContent()
    }

    override fun listItemSwiped(task: Task) {
        deleteTask(task)
            viewInterface.changeButtonClickable(editTextString.isNotEmpty())
        showContent()
    }

    override fun buttonAddTaskClicked(message: String) {
        model.addTask(message)
        editTextString = ""
        viewInterface.clearEditText()
        viewInterface.changeButtonClickable(false)
        showContent()
    }

    override fun editTextTextChanged(string: String) {
        editTextString = string
        viewInterface.changeButtonClickable(string.isNotEmpty())
    }

    private fun showContent() {
        val taskList = model.getMyList()
        if (taskList.isEmpty()) {
            viewInterface.changeEmptyTextMessageVisibility(true)
            viewInterface.changeListVisibility(false)
        }
        else {
            viewInterface.changeEmptyTextMessageVisibility(false)
            viewInterface.changeListVisibility(true)
            viewInterface.updateList(taskList)
        }
    }

    companion object {
        @Suppress("unused")
        private const val TAG = "MainPresenter"
    }
}