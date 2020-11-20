package ru.faizovr.todo.data

import ru.faizovr.todo.domain.model.Task

interface Repository {
    fun getListFromSharedPreference(): List<Task>
    fun getEditablePositionFromSharedPreference(): Int
    fun getIdFromSharedPreference(): Long
    fun saveListToSharedPreference(taskList: List<Task>)
    fun saveEditablePositionToSharedPreference(editablePosition: Int)
    fun saveIdToSharedPreference(id: Long)

}