package ru.faizovr.todo.presentation.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task_view_holder.view.*
import ru.faizovr.todo.R
import ru.faizovr.todo.data.Task
import ru.faizovr.todo.data.TaskState

class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun setImage (taskState: TaskState) {
        if (taskState == TaskState.EDIT) {
            itemView.button_edit_task.setImageResource(R.drawable.ic_round_close_48)
        } else {
            itemView.button_edit_task.setImageResource(R.drawable.ic_round_edit_48)
        }
    }

    fun bind(task: Task) {
        with(itemView) {
            text_id.text = task.id.toString()
            setImage(task.taskState)
            text_task.text = task.message
        }
    }
}
