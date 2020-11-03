package ru.faizovr.todo.presentation.adapter

import android.R.attr.data
import android.R.attr.logoDescription
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task_view_holder.view.*
import ru.faizovr.todo.R
import ru.faizovr.todo.data.Task
import ru.faizovr.todo.presentation.viewholder.TaskViewHolder


class ListRecyclerViewAdapter(private val onEditButtonClickListener: (task: Task) -> Unit):
    RecyclerView.Adapter<TaskViewHolder>() {

    private var taskList: MutableList<Task> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_view_holder, parent, false)
        return TaskViewHolder(view)
    }

    fun updateList(newList: List<Task>) {
        val diffResult = DiffUtil.calculateDiff(ListDiffUtilCallback(taskList, newList))
        Log.d(TAG, "updateList: FIRST")
        taskList.clear()
        taskList = newList.toMutableList()
        diffResult.dispatchUpdatesTo(this)
        Log.d(TAG, "updateList: SECOND")

    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val o = payloads[0] as Bundle
            Log.d(TAG, "onBindViewHolder: ${o}")
            Log.d(TAG, "onBindViewHolder: ${o.getString("Message")}")
            for (key in o.keySet()) {
                Log.d(TAG, "onBindViewHolder: $key")
                if (key == "Message") {
                    Toast.makeText(holder.itemView.context, "Contact $position : Name Changed", Toast.LENGTH_SHORT).show()
                    holder.itemView.text_task.text = o.getString("Message")
                    holder.itemView.setOnClickListener{
                        onEditButtonClickListener(taskList[position])
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
        holder.itemView.setOnClickListener {
            onEditButtonClickListener(taskList[position])
        }
    }

    fun getTaskFromView(position: Int): Task = taskList[position]

    companion object {
        @Suppress("unused")
        private const val TAG = "ListRecyclerViewAdapter"
    }
}