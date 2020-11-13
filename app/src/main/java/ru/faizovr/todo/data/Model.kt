package ru.faizovr.todo.data
class Model {

    private val taskList: MutableList<Task> = mutableListOf()
    private var id: Long = 0
    private var editablePosition: Int = -1

    fun getMyList(): List<Task> =
            taskList

    fun addTask(message: String) {
        val newTask = Task(id++, message)
        taskList.add(newTask)
    }

    fun swapTask(fromPosition: Int, toPosition: Int) {
        if (fromPosition in 0 until taskList.size && toPosition in 0 until taskList.size) {
            val temp: Task = taskList[fromPosition]
            taskList[fromPosition] = taskList[toPosition]
            taskList[toPosition] = temp
        }
    }

    fun getEditableTaskPosition(): Int =
            editablePosition

    fun getEditableTaskMessage(): String {
        return if (getEditableTaskPosition() in 0 until taskList.size)
            taskList[getEditableTaskPosition()].message
        else
            ""
    }

    fun setTaskState(position: Int, taskState: TaskState) {
        if (position in 0 until taskList.size) {
            taskList[position].taskState = taskState
            editablePosition = if (taskState == TaskState.EDIT)
                position
            else
                -1
        }
    }

    fun deleteTask(position: Int) {
        if (position in 0 until taskList.size)
            taskList.removeAt(position)
    }

    fun setTaskMessage(position: Int, message: String) {
        if (position in 0 until taskList.size)
            taskList[position].message = message
    }

    fun getTaskFromPosition(position: Int): Task? {
        if (position in 0 until taskList.size) {
            return taskList[position]
        }
        return null
    }

    fun getCopyList(): List<Task> =
            taskList.map(Task::copy)
}

