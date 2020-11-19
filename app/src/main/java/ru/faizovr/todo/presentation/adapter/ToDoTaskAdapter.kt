package ru.faizovr.todo.presentation.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.faizovr.todo.R
import ru.faizovr.todo.presentation.viewholder.TaskDataView
import ru.faizovr.todo.presentation.viewholder.TaskViewHolder

class ToDoTaskAdapter(
        private val onEditButtonClickListener: (position: Int) -> Unit,
        private val onCheckBoxClickListener: (position: Int) -> Unit,
        private val onTaskClickListener: (position: Int) -> Unit
) :
        RecyclerView.Adapter<TaskViewHolder>() {

    private var taskList: List<TaskDataView> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.task_view_holder, parent, false)
        return TaskViewHolder(view)
    }

    fun updateList(newList: List<TaskDataView>) {
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(ListDiffUtilCallback(taskList, newList))
        Log.d(TAG, "updateList: old ${taskList.map(TaskDataView::message)}")
        Log.d(TAG, "updateList: new ${newList.map(TaskDataView::message)}")
        taskList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int =
            taskList.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val bundle: Bundle = payloads[0] as Bundle
            for (key: String in bundle.keySet()) {
                when (key) {
                    KEY_CHECKBOX -> holder.setState(bundle.getBoolean(KEY_CHECKBOX))
                    KEY_MESSAGE -> holder.setMessage(bundle.getString(KEY_MESSAGE).toString())
                    KEY_NEW_POSITION -> holder.setOnClickListeners(onEditButtonClickListener, onCheckBoxClickListener, onTaskClickListener, position)
                    KEY_IMAGE -> holder.setImage(bundle.getInt(KEY_IMAGE))
                }
            }
        }
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int): Unit =
            holder.bind(taskList[position], position, onEditButtonClickListener, onCheckBoxClickListener, onTaskClickListener)

    companion object {
        private const val TAG = "ListRecyclerViewAdapter"
        const val KEY_CHECKBOX = "Checkbox"
        const val KEY_MESSAGE = "Message"
        const val KEY_NEW_POSITION = "NewPosition"
        const val KEY_IMAGE = "Image"
    }
}