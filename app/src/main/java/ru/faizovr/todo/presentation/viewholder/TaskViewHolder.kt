package ru.faizovr.todo.presentation.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task_view_holder.view.*

class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val taskTextView: TextView = itemView.text_task

}