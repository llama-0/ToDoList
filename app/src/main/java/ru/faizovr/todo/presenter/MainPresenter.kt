package ru.faizovr.todo.presenter

import ru.faizovr.todo.model.Task
import ru.faizovr.todo.mvp.MainContract

class MainPresenter (private var viewInterface: MainContract.ViewInterface, private val taskList: MutableList<Task> )
    : MainContract.PresenterInterface {

    private val TAG = "MainPresenter"

    override fun getMyList(): List<Task> {
        return taskList
    }

    override fun showList() {
        if (taskList.isEmpty())
            viewInterface.displayNoList()
        else
            viewInterface.displayList()
    }

    override fun addTaskToList(message: String) {
        taskList.add(Task(message))
        showList()
    }
}