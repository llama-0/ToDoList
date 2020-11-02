package ru.faizovr.todo.presentation.presenter

import ru.faizovr.todo.data.Model
import ru.faizovr.todo.data.Task
import ru.faizovr.todo.presentation.TaskListContract

class TaskListPresenter (private var viewInterface: TaskListContract.ViewInterface, private val model: Model)
    : TaskListContract.PresenterInterface {

    private var editTextString: String = ""

    override fun init() = showContent()

    override fun listItemSwipped(position: Int) {
        model.deleteTask(position)
        viewInterface.changeButtonClickable(editTextString.isNotEmpty())
        showContent()
    }

    override fun buttonAddTaskClicked(message: String) {
        model.addTask(Task(message))
        editTextString = ""
        viewInterface.clearEditText()
        viewInterface.changeButtonClickable(false)
        showContent()
    }

    override fun textChanged(string: String) {
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

        private const val TAG = "MainPresenter"

    }
}