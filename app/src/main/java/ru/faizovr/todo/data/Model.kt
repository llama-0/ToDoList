package ru.faizovr.todo.data

class Model {
    private val taskList: MutableList<Task> = mutableListOf()

    fun getMyList(): List<Task> = taskList

    fun addTask(message: String) {
        val newTask = Task(id, message)
        id++
        taskList.add(newTask)
    }

    fun swapTask(fromPosition: Int, toPosition: Int) {
        val temp : Task = taskList[fromPosition]
        taskList[fromPosition] = taskList[toPosition]
        taskList[toPosition] = temp
    }

    fun getEditableTaskPosition(): Int =
            taskList.indexOfFirst { it.taskState == TaskState.EDIT }

    fun getEditableTaskMessage(): String =
            taskList[taskList.indexOfFirst { it.taskState == TaskState.EDIT }].message

    fun setTaskState(position: Int, taskState: TaskState) {
        taskList[position].taskState = taskState
    }

    fun deleteTask(position: Int) {
        taskList.removeAt(position)
    }

    fun setTaskMessage(position: Int, message: String) {
        taskList[position].message = message
    }

    companion object {
        private const val TAG = "Model"
        private var id: Long = 0
    }
}


