package ru.faizovr.todo.presentation.adapter

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import ru.faizovr.todo.data.Task

class ListDiffUtilCallback(private val oldList: List<Task>, private val newList: List<Task>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = when {
        oldList[oldItemPosition].message == newList[newItemPosition].message -> false
        oldList[oldItemPosition].taskState == newList[newItemPosition].taskState -> false
        else -> true
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val newTask: Task = newList[newItemPosition]
        val oldTask: Task = oldList[oldItemPosition]
        val diff: Bundle = Bundle()
        if (oldItemPosition != newItemPosition) {
            diff.putInt(ToDoTaskAdapter.KEY_NEW_POSITION, newItemPosition)
        }
        if (newTask.message != oldTask.message) {
            diff.putString(ToDoTaskAdapter.KEY_MESSAGE, newTask.message)
        }
        if (newTask.taskState != oldTask.taskState) {
            diff.putString(ToDoTaskAdapter.KEY_TASK_STATE, newTask.taskState.toString())
        }
        return diff
    }

    companion object {
        @Suppress("unused")
        private const val TAG = "getChangePayload"
    }
}