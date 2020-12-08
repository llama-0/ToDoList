package ru.faizovr.todo.data

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.faizovr.todo.domain.model.Task

class RepositoryImplementation(private val sharedPreferences: SharedPreferences)
    : Repository {

    override fun getListFromSharedPreference(): List<Task> {
        val jsonString = sharedPreferences.getString(PREFS_TASK_LIST_KEY, "")
        if (jsonString?.isEmpty() == true)
            return mutableListOf()
        val type = object : TypeToken<List<Task>>() {}.type
        return Gson().fromJson<MutableList<Task>>(jsonString, type)
    }

    override fun getEditablePositionFromSharedPreference(): Int =
            sharedPreferences.getInt(PREFS_EDITABLE_POSITION_KEY, -1)

    override fun getIdFromSharedPreference(): Long =
            sharedPreferences.getLong(PREFS_ID_KEY, 0)

    override fun saveListToSharedPreference(taskList: List<Task>) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val jsonString: String = Gson().toJson(taskList)
        editor.putString(PREFS_TASK_LIST_KEY, jsonString)
        editor.apply()
    }

    override fun saveEditablePositionToSharedPreference(editablePosition: Int) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(PREFS_EDITABLE_POSITION_KEY, editablePosition)
        editor.apply()
    }

    override fun saveIdToSharedPreference(id: Long) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putLong(PREFS_ID_KEY, id)
        editor.apply()
    }

    companion object {
        private const val PREFS_TASK_LIST_KEY = "PREFS_TASK_LIST_KEY"
        private const val PREFS_ID_KEY = "PREFS_ID_KEY"
        private const val PREFS_EDITABLE_POSITION_KEY = "PREFS_EDITABLE_POSITION_KEY"
    }
}