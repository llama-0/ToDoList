package ru.faizovr.todo.domain.model

import ru.faizovr.todo.data.Repository

class Model(private val repository: Repository) {

    private val taskList: MutableList<Task> = mutableListOf()
    private var id: Long = 0
    private var editablePosition: Int = -1

    init {
        loadDataFromSharedPreference()
    }

    private fun loadDataFromSharedPreference() {
        val taskList = repository.getListFromSharedPreference() as MutableList<Task>
        this.taskList.clear()
        this.taskList.addAll(taskList)
        id = repository.getIdFromSharedPreference()
        editablePosition = repository.getEditablePositionFromSharedPreference()
    }

    fun saveDataToSharedPreference() {
        repository.saveListToSharedPreference(taskList)
        repository.saveEditablePositionToSharedPreference(editablePosition)
        repository.saveIdToSharedPreference(id)
    }

    fun getMyList(): List<Task> =
            taskList

    fun addTask(message: String) {
        val newTask = Task(id++, message)
        taskList.add(newTask)
    }

    private fun swapEditableTask(fromPosition: Int, toPosition: Int) {
        if (taskList[fromPosition].taskState == TaskState.EDIT) {
            editablePosition = toPosition
        } else if (taskList[toPosition].taskState == TaskState.EDIT) {
            editablePosition = fromPosition
        }
    }

    fun swapTask(fromPosition: Int, toPosition: Int) {
        if (fromPosition in 0 until taskList.size && toPosition in 0 until taskList.size) {
            swapEditableTask(fromPosition, toPosition)
            val temp: Task = taskList[fromPosition]
            taskList[fromPosition] = taskList[toPosition]
            taskList[toPosition] = temp
        }
    }

    fun getEditableTaskPosition(): Int =
            editablePosition

    fun getEditableTaskMessage(): String =
            if (getEditableTaskPosition() in 0 until taskList.size)
                taskList[getEditableTaskPosition()].message
            else
                ""

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
        if (editablePosition == position)
            editablePosition = -1
        if (position in 0 until taskList.size)
            taskList.removeAt(position)
    }

    fun setTaskMessage(position: Int, message: String) {
        if (position in 0 until taskList.size)
            taskList[position].message = message
    }

    fun getTaskFromPosition(position: Int): Task? =
            if (position in 0 until taskList.size)
                taskList[position]
            else
                null

    fun getCopyList(): List<Task> =
            taskList.map(Task::copy)
}

