package ru.faizovr.todo.data

class Model {
    private val taskList: MutableList<Task> = mutableListOf()

    fun getMyList(): List<Task> = taskList

    fun addTask(task: Task) : Boolean = taskList.add(task)

    fun deleteTask(task: Task) {
        taskList.remove(task)
    }

    fun changeTaskState(task: Task, taskState: TaskState) {
        task.taskState = taskState
    }

    fun isContain(task: Task) : Boolean = taskList.contains(task)
}