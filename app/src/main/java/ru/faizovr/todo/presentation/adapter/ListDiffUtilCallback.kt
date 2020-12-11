package ru.faizovr.todo.presentation.adapter

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import ru.faizovr.todo.presentation.viewholder.TaskDataView

class ListDiffUtilCallback(
    private val oldList: List<TaskDataView>,
    private val newList: List<TaskDataView>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = when {
        oldList[oldItemPosition].isCheckBoxActive == newList[newItemPosition].isCheckBoxActive -> false
        oldList[oldItemPosition].message == newList[newItemPosition].message -> false
        oldList[oldItemPosition].editButtonImageId == newList[newItemPosition].editButtonImageId -> false
        else -> true
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val newTask: TaskDataView = newList[newItemPosition]
        val oldTask: TaskDataView = oldList[oldItemPosition]
        val diff = Bundle()
        if (oldItemPosition != newItemPosition) {
            diff.putInt(ToDoTaskAdapter.KEY_NEW_POSITION, newItemPosition)
        }
        if (newTask.isCheckBoxActive != oldTask.isCheckBoxActive) {
            diff.putBoolean(ToDoTaskAdapter.KEY_CHECKBOX, newTask.isCheckBoxActive)
        }
        if (newTask.message != oldTask.message) {
            diff.putString(ToDoTaskAdapter.KEY_MESSAGE, newTask.message)
        }
        if (newTask.editButtonImageId != oldTask.editButtonImageId) {
            diff.putInt(ToDoTaskAdapter.KEY_IMAGE, newTask.editButtonImageId)
        }
        return diff
    }

    companion object {
        @Suppress("unused")
        private const val TAG = "getChangePayload"
    }
}