package ru.faizovr.todo.presentation.presenter

import ru.faizovr.todo.data.Model
import ru.faizovr.todo.data.Task
import ru.faizovr.todo.presentation.TaskListContract

class TaskListPresenter (private var viewInterface: TaskListContract.ViewInterface, val model: Model )
    : TaskListContract.PresenterInterface {

    private val TAG = "MainPresenter"

    override fun getList(): List<Task> = model.getMyList()

    override fun init() = showContent()

    override fun buttonAddTaskClicked(message: String) {
        model.addTask(Task(message))
        viewInterface.clearEditText()
        viewInterface.changeButtonClickable(false)
        showContent()
    }

    override fun textChanged(string: String) = viewInterface.changeButtonClickable(string.isNotEmpty())

    fun showContent() {
        val taskList = model.getMyList()
        if (taskList.isEmpty())
            viewInterface.displayNoList()
        else
            viewInterface.displayList(taskList)
    }
}