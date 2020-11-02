package ru.faizovr.todo.data

class Task(var message: String)
{
    val id: Long
    var taskState: TaskState = TaskState.SHOW

    init {
        id = fillId
        fillId++
    }

    override fun equals(other: Any?): Boolean {
        if (other is Task) {
            val other1: Task = other
            if (message != other1.message)
                return false
            if (taskState != other1.taskState)
                return false
            return true
        }
        return false
    }

    companion object {
        var fillId: Long = 0
    }
}