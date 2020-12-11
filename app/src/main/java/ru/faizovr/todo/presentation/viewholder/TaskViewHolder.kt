package ru.faizovr.todo.presentation.viewholder

import android.graphics.Paint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task_view_holder.view.*

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun setMessage(message: String) {
        itemView.text_task.text = message
    }

    fun setState(isChecked: Boolean) {
        itemView.checkbox_is_complete.isChecked = isChecked
        if (isChecked) {
            itemView.text_task.paintFlags =
                itemView.text_task.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            itemView.text_task.paintFlags = 0
        }
    }

    fun setImage(imageId: Int) {
        itemView.button_edit_task.setImageResource(imageId)
    }

    fun setOnClickListeners(
        onEditButtonClickListener: (position: Int) -> Unit,
        onCheckBoxClickListener: (position: Int) -> Unit,
        onTaskClickListener: (position: Int) -> Unit,
        position: Int
    ) {
        itemView.button_edit_task.setOnClickListener {
            onEditButtonClickListener(position)
        }
        itemView.checkbox_is_complete.setOnClickListener {
            onCheckBoxClickListener(position)
        }
        itemView.setOnClickListener {
            onTaskClickListener(position)
        }
    }

    fun bind(
        task: TaskDataView,
        position: Int,
        onEditButtonClickListener: (position: Int) -> Unit,
        onCheckBoxClickListener: (position: Int) -> Unit,
        onTaskClickListener: (position: Int) -> Unit
    ) {
        setState(task.isCheckBoxActive)
        setMessage(task.message)
        setImage(task.editButtonImageId)
        setOnClickListeners(
            onEditButtonClickListener,
            onCheckBoxClickListener,
            onTaskClickListener,
            position
        )
    }
}
