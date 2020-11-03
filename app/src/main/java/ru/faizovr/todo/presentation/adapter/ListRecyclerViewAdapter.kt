package ru.faizovr.todo.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.faizovr.todo.R
import ru.faizovr.todo.data.Task
import ru.faizovr.todo.presentation.viewholder.TaskViewHolder
import java.util.*
import kotlin.collections.ArrayList

class ListRecyclerViewAdapter():
    RecyclerView.Adapter<TaskViewHolder>() {

    private val taskList: MutableList<Task> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_view_holder, parent, false)
        return TaskViewHolder(view)
    }

    fun updateList(newList: List<Task>) {
        val diffResult = DiffUtil.calculateDiff(ListDiffUtilCallback(taskList, newList))
        taskList.clear()
        taskList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    fun getTaskFromView(position: Int): Task = taskList[position]

    companion object {
        @Suppress("unused")
        private const val TAG = "ListRecyclerViewAdapter"
    }
}