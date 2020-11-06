package ru.faizovr.todo.presentation.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task_view_holder.view.*
import ru.faizovr.todo.R
import ru.faizovr.todo.data.Task
import ru.faizovr.todo.data.TaskState

class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun setMessage(message: String) {
        itemView.text_task.text = message
    }

    fun setId(id: Long) {
        itemView.text_id.text = id.toString()
    }

    fun setImage (taskState: TaskState) {
        if (taskState == TaskState.EDIT) {
            itemView.button_edit_task.setImageResource(R.drawable.ic_round_close_48)
        } else {
            itemView.button_edit_task.setImageResource(R.drawable.ic_round_edit_48)
        }
    }

    fun setOnClickListener(onEditButtonClickListener:  (position: Int) -> Unit, position: Int) {
        itemView.button_edit_task.setOnClickListener{
            onEditButtonClickListener(position)
        }
    }

    fun bind(task: Task, position: Int, onEditButtonClickListener: (position: Int) -> Unit) {
        setId(task.id)
        setMessage(task.message)
        setImage(task.taskState)
        setOnClickListener(onEditButtonClickListener, position)
    }
}
