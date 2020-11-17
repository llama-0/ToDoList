package ru.faizovr.todo.domain.model

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Model(private val prefs: SharedPreferences) {

    private var taskList: MutableList<Task> = mutableListOf()
    private var id: Long = 0
    private var editablePosition: Int = -1

    init {
        getDataFromSharedPreference()
    }

    private fun getDataFromSharedPreference() {
        val jsonString = prefs.getString(PREFS_TASK_LIST_KEY, "")
        val type = object : TypeToken<List<Task>>(){}.type
        taskList = Gson().fromJson<MutableList<Task>>(jsonString, type) ?: mutableListOf()
        id = prefs.getLong(PREFS_ID_KEY, id)
        editablePosition = prefs.getInt(PREFS_EDITABLE_POSITION_KEY, editablePosition)
    }

    fun setDataToSharedPreference() {
        val editor: SharedPreferences.Editor = prefs.edit()
        val jsonString: String = Gson().toJson(taskList)
        editor.putString(PREFS_TASK_LIST_KEY, jsonString)
        editor.putLong(PREFS_ID_KEY, id)
        editor.putInt(PREFS_EDITABLE_POSITION_KEY, editablePosition)
        editor.apply()
    }

    fun getMyList(): List<Task> =
            taskList

    fun addTask(message: String) {
        val newTask = Task(id++, message)
        taskList.add(newTask)
    }

    fun swapTask(fromPosition: Int, toPosition: Int) {
        if (fromPosition in 0 until taskList.size && toPosition in 0 until taskList.size) {
            if (taskList[fromPosition].taskState == TaskState.EDIT) {
                editablePosition = toPosition
            } else if (taskList[toPosition].taskState == TaskState.EDIT) {
                editablePosition = fromPosition
            }
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

    companion object {
        private const val PREFS_TASK_LIST_KEY = "PREFS_TASK_LIST_KEY"
        private const val PREFS_ID_KEY = "PREFS_ID_KEY"
        private const val PREFS_EDITABLE_POSITION_KEY = "PREFS_EDITABLE_POSITION_KEY"
    }
}

