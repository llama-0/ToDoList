package ru.faizovr.todo.presentation.presenter

import ru.faizovr.todo.data.Model
import ru.faizovr.todo.data.Task
import ru.faizovr.todo.presentation.TaskListContract

class TaskListPresenter (private var viewInterface: TaskListContract.ViewInterface, val model: Model )
    : TaskListContract.PresenterInterface {

    private val TAG = "MainPresenter"

    override fun getMyList(): List<Task> {
        return model.taskList
    }

    override fun init() {
        if (model.taskList.isEmpty())
            viewInterface.displayNoList()
        else
            viewInterface.displayList(model.taskList)
    }

    override fun addTaskToList(message: String) {
        model.taskList.add(Task(message))
        init()
    }

    override fun getMyModel(): Model = model
}