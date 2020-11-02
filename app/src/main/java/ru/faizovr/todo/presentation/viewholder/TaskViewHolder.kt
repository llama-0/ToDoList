package ru.faizovr.todo.presentation.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task_view_holder.view.*
import ru.faizovr.todo.R
import ru.faizovr.todo.data.Task
import ru.faizovr.todo.data.TaskState

class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private fun setImage (task: Task) {
        if (task.taskState == TaskState.EDIT) {
            itemView.button_edit_task.setImageResource(R.drawable.ic_round_close_48)
        } else {
            itemView.button_edit_task.setImageResource(R.drawable.ic_round_edit_48)
        }
    }

    fun bind(task: Task, clickListener: (task: Task) -> Unit) =  with(itemView) {
        text_id.text = task.id.toString()
        text_task.text = task.message
        setImage(task)
        button_edit_task.setOnClickListener {
            clickListener(task)
        }
    }
}
