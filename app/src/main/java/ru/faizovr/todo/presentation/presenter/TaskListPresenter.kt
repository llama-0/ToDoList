package ru.faizovr.todo.presentation.presenter

import ru.faizovr.todo.data.Model
import ru.faizovr.todo.data.Task
import ru.faizovr.todo.presentation.TaskListContract

class TaskListPresenter (private var viewInterface: TaskListContract.ViewInterface, val model: Model )
    : TaskListContract.PresenterInterface {

    private val TAG = "MainPresenter"

    override fun getList(): List<Task> = model.getMyList()

    override fun init() = showContent()

    fun showContent() {
        val taskList = model.getMyList()
        if (taskList.isEmpty())
            viewInterface.displayNoList()
        else
            viewInterface.displayList(taskList)
    }

    override fun addTaskToList(message: String) {
        model.addTask(Task(message))
        showContent()
    }

    override fun getMyModel(): Model = model
}