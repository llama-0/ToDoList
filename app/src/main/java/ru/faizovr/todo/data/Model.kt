package ru.faizovr.todo.data

class Model {
    private val taskList: MutableList<Task> = mutableListOf()

    fun getMyList(): List<Task> = taskList

    fun addTask(message: String) {
        val newTask = Task(id, message)
        id++
        taskList.add(newTask)
    }

    fun swapTask(task: Task, toPosition: Int) {
        val taskPosition = taskList.indexOf(task)
        taskList[taskPosition] = taskList[toPosition]
        taskList[toPosition] = task
    }

    fun deleteTask(task: Task) {
        taskList.remove(task)
    }

    fun isContain(task: Task) : Boolean = taskList.contains(task)

    companion object {
        private var id: Long = 0
    }
}