package ru.faizovr.todo.presentation.presenter

import ru.faizovr.todo.data.Model
import ru.faizovr.todo.data.Task
import ru.faizovr.todo.presentation.TaskListContract

class TaskListPresenter (private var viewInterface: TaskListContract.ViewInterface, private val model: Model)
    : TaskListContract.PresenterInterface {

    override fun init() = showContent()

    override fun listItemSwapped(position: Int) {
        model.deleteTask(position)
        viewInterface.changeButtonClickable(false)
        showContent()
    }

    override fun buttonAddTaskClicked(message: String) {
        model.addTask(Task(message))
        viewInterface.clearEditText()
        viewInterface.changeButtonClickable(false)
        showContent()
    }

    override fun textChanged(string: String) {
        viewInterface.changeButtonClickable(string.isNotEmpty())
    }

    override fun getList(): List<Task> = model.getMyList()

    private fun showContent() {
        val taskList = model.getMyList()
        if (taskList.isEmpty()) {
            viewInterface.changeEmptyTextMessageVisibility(true)
            viewInterface.changeListVisibility(false)
        }
        else {
            viewInterface.changeEmptyTextMessageVisibility(false)
            viewInterface.changeListVisibility(true)
            viewInterface.displayList(taskList)
        }
    }

    companion object {

        private const val TAG = "MainPresenter"

    }
}