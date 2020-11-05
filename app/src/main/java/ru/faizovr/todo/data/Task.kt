package ru.faizovr.todo.data

class Task(val id: Long, var message: String, var taskState: TaskState = TaskState.DEFAULT) {

    override fun equals(other: Any?): Boolean {
        if (other is Task) {
            if (other.message != message)
                return false
            if (other.taskState != taskState)
                return false
        } else {
            return true
        }
        return false
    }

    fun copy(): Task =
        Task(id, message, taskState)
}