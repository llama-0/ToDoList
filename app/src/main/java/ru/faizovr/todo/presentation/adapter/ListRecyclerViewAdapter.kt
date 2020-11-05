package ru.faizovr.todo.presentation.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task_view_holder.view.*
import ru.faizovr.todo.R
import ru.faizovr.todo.data.Task
import ru.faizovr.todo.data.TaskState
import ru.faizovr.todo.presentation.viewholder.TaskViewHolder
import kotlin.collections.ArrayList

class ListRecyclerViewAdapter(private val onEditButtonClickListener: (position: Int) -> Unit):
    RecyclerView.Adapter<TaskViewHolder>() {

    private var taskList: List<Task> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_view_holder, parent, false)
        return TaskViewHolder(view)
    }

    fun updateList(newList: List<Task>) {
        val diffResult = DiffUtil.calculateDiff(ListDiffUtilCallback(taskList, newList))
        Log.d(TAG, "updateList: old ${taskList.map { it.taskState.toString() }}")
        Log.d(TAG, "updateList: new ${newList.map { it.taskState.toString() }}")
        taskList = newList.map { it.copy() }.toList()
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val o = payloads[0] as Bundle
            for (key in o.keySet()) {
                if (key == "Message") {
                    holder.itemView.text_task.text = o.getString("Message")
                }
                if (key == "NewPosition") {
                    holder.itemView.button_edit_task.setOnClickListener{
                        onEditButtonClickListener(o.getInt("NewPosition"))
                    }
                }
                if (key == "TaskState") {
                    val string = o.getString("TaskState")
                    val taskState = when (string.equals("EDIT")) {
                        true -> TaskState.EDIT
                        else -> TaskState.DEFAULT
                    }
                    holder.setImage(taskState)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
        holder.itemView.button_edit_task.setOnClickListener{
            onEditButtonClickListener(position)
        }
    }

    companion object {
        @Suppress("unused")
        private const val TAG = "ListRecyclerViewAdapter"
    }
}