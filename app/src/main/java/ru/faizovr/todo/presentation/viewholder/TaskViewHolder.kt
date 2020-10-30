package ru.faizovr.todo.presentation.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.faizovr.todo.R

class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val taskTextView: TextView = itemView.findViewById(R.id.text_task)

}