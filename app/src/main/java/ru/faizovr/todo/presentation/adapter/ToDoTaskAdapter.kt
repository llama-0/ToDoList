package ru.faizovr.todo.presentation.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.faizovr.todo.R
import ru.faizovr.todo.data.Task
import ru.faizovr.todo.data.TaskState
import ru.faizovr.todo.presentation.viewholder.TaskViewHolder
import kotlin.collections.ArrayList

class ToDoTaskAdapter(private val onEditButtonClickListener: (position: Int) -> Unit):
    RecyclerView.Adapter<TaskViewHolder>() {

    private var taskList: List<Task> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_view_holder, parent, false)
        return TaskViewHolder(view)
    }

    fun updateList(newList: List<Task>) {
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(ListDiffUtilCallback(taskList, newList))
        Log.d(TAG, "updateList: old ${taskList.map { it.taskState.toString() }}")
        Log.d(TAG, "updateList: new ${newList.map { it.taskState.toString() }}")
        taskList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int
            = taskList.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val bundle: Bundle = payloads[0] as Bundle
            for (key: String in bundle.keySet()) {
                when (key) {
                    KEY_MESSAGE -> {
                        holder.setMessage(bundle.getString(KEY_MESSAGE).toString())
                    }
                    KEY_NEW_POSITION -> {
                        holder.setOnClickListener(onEditButtonClickListener, position)
                    }
                    KEY_TASK_STATE -> {
                        val string = bundle.getString(KEY_TASK_STATE)
                        val taskState = when (string.equals("EDIT")) {
                            true -> TaskState.EDIT
                            else -> TaskState.DEFAULT
                        }
                        holder.setImage(taskState)
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int): Unit =
            holder.bind(taskList[position], position, onEditButtonClickListener)

    companion object {
        @Suppress("unused")
        private const val TAG = "ListRecyclerViewAdapter"
        const val KEY_MESSAGE = "Message"
        const val KEY_NEW_POSITION = "NewPosition"
        const val KEY_TASK_STATE = "TaskState"
    }
}