package ru.faizovr.todo.presentation.presenter

import ru.faizovr.todo.domain.model.Model
import ru.faizovr.todo.presentation.contract.TaskContract

class TaskPresenter(
    private val view: TaskContract.View,
    private val model: Model
) : TaskContract.Presenter {

    override fun init(taskId: Long?) {
        if (taskId != null) {
            val task = model.getTaskById(taskId)
            val message = task?.message
            if (message != null) {
                view.showTaskMessage(message)
            }
        }
    }
}