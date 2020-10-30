package ru.faizovr.todo.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.faizovr.todo.R
import ru.faizovr.todo.data.Task
import ru.faizovr.todo.presentation.viewholder.TaskViewHolder

class ListRecyclerViewAdapter(var taskList: List<Task>?):
    RecyclerView.Adapter<TaskViewHolder>() {

    private val TAG = "ListRecyclerViewAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_view_holder, parent, false)
        return TaskViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        if (taskList != null) {
            return taskList!!.size
        } else {
            return 0
        }
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task: Task? = taskList?.get(position)
        if (task != null) {
            holder.taskTextView.text = task.message
        } else {
            throw NullPointerException()
        }
    }
}