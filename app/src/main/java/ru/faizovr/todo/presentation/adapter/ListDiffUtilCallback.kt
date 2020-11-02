package ru.faizovr.todo.presentation.adapter

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import ru.faizovr.todo.data.Task

class ListDiffUtilCallback(private val oldList: List<Task>, private val newList: List<Task>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        Log.d(TAG, "areItemsTheSame: oldList[oldItemPosition].id = ${oldList[oldItemPosition].id} newList[newItemPosition].id = ${newList[newItemPosition].id}")
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].message == newList[newItemPosition].message
    }

    companion object {
        private const val TAG = "ListDiffUtilCallback"
    }

}