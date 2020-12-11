package ru.faizovr.todo.data

import ru.faizovr.todo.domain.model.Task

interface Repository {
    fun getListFromSharedPreference(): List<Task>
    fun getEditablePositionFromSharedPreference(): Int
    fun getIdFromSharedPreference(): Long
//    fun getDeletedTaskIdFromSharedPreference(): Long // delete from shared prefs when done
//    fun getDeletedTaskFromSharedPreference(): Task // delete from shared prefs when done

    fun saveListToSharedPreference(taskList: List<Task>)
    fun saveEditablePositionToSharedPreference(editablePosition: Int)
    fun saveIdToSharedPreference(id: Long)
//    fun saveDeletedTaskIdToSharedPreference(id: Long)
//    fun saveDeletedTaskToSharedPreference(task: Task)

//    fun removeDeletedTaskIdFromSharedPreference(id: Long)
//    fun removeDeletedTaskFromSharedPreference(task: Task)
}