package ru.faizovr.todo.presentation.presenter

import ru.faizovr.todo.presentation.contract.TaskContract
import ru.faizovr.todo.presentation.viewholder.TaskDataView

class TaskPresenter(
        private val view: TaskContract.View,
        private val taskDataView: TaskDataView
) : TaskContract.Presenter {

    override fun init() {
        val message = taskDataView.message
        view.showTaskMessage(message)
    }
}