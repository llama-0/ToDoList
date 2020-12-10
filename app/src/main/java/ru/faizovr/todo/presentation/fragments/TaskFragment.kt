package ru.faizovr.todo.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_task.*
import ru.faizovr.todo.R
import ru.faizovr.todo.ToDoApplication
import ru.faizovr.todo.presentation.activity.ToDoActivity
import ru.faizovr.todo.presentation.contract.TaskContract
import ru.faizovr.todo.presentation.presenter.TaskPresenter

class TaskFragment : Fragment(R.layout.fragment_task), TaskContract.View {

    private var taskPresenter: TaskPresenter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as ToDoActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupPresenter()
    }

    private fun setupPresenter() {
        val id = arguments?.getLong(TASK_ID_KEY)
        val app: ToDoApplication = activity?.application as ToDoApplication
        taskPresenter = TaskPresenter(this, app.model)
        taskPresenter?.init(id)
    }

    override fun showTaskMessage(message: String) {
        text_task_message.text = message
    }

    companion object {
        const val FRAGMENT_TAG = "TaskFragment"
        const val TASK_ID_KEY = "TASK_ID"
    }
}