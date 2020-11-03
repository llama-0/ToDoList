package ru.faizovr.todo.presentation.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task_view_holder.view.*
import ru.faizovr.todo.R
import ru.faizovr.todo.data.Task

class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bind(task: Task) =  with(itemView) {
        text_id.text = task.id.toString()
        text_task.text = task.message
    }
}
