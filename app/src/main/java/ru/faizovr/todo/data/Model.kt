package ru.faizovr.todo.data

class Model {
    private val taskList: MutableList<Task> = mutableListOf()

    fun getMyList(): List<Task> = taskList

    fun addTask(task: Task) = taskList.add(task)

    fun deleteTask(position: Int) = taskList.removeAt(position)
}