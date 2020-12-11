package ru.faizovr.todo.domain.model

class Task(val id: Long, var message: String, var taskState: TaskState = TaskState.DEFAULT) {

    fun copy(): Task =
        Task(id, message, taskState)
}