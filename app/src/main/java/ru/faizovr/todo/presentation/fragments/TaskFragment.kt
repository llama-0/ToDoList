package ru.faizovr.todo.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_task.*
import ru.faizovr.todo.R
import ru.faizovr.todo.presentation.activity.ToDoActivity
import ru.faizovr.todo.presentation.contract.TaskContract
import ru.faizovr.todo.presentation.presenter.TaskPresenter
import ru.faizovr.todo.presentation.viewholder.TaskDataView

class TaskFragment(private var taskDataView: TaskDataView?) : Fragment(R.layout.fragment_task), TaskContract.View {

    private var taskPresenter: TaskPresenter? = null

    constructor() : this(null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as ToDoActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (savedInstanceState != null) {
            taskDataView = savedInstanceState.getParcelable<TaskDataView>(TASK_DATA_VIEW_KEY)
        }
        setupPresenter()
    }

    private fun setupPresenter() {
        val taskDataView1 = taskDataView
        if (taskDataView1 != null) {
            taskPresenter = TaskPresenter(this, taskDataView1)
            taskPresenter?.init()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(TASK_DATA_VIEW_KEY, taskDataView)
    }

    override fun showTaskMessage(message: String) {
        text_task_message.text = message
    }

    companion object {
        private const val TASK_DATA_VIEW_KEY = "TaskDataView"
    }

}