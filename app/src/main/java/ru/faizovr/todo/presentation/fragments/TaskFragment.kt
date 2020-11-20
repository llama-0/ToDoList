package ru.faizovr.todo.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_task.*
import ru.faizovr.todo.R
import ru.faizovr.todo.ToDoApplication
import ru.faizovr.todo.presentation.contract.TaskContract
import ru.faizovr.todo.presentation.presenter.TaskPresenter
import ru.faizovr.todo.presentation.viewholder.TaskDataView

class TaskFragment(private val taskDataView: TaskDataView) : Fragment(R.layout.fragment_task), TaskContract.View {

    private var taskPresenter: TaskPresenter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPresenter()
    }

    private fun setupPresenter() {
        taskPresenter = TaskPresenter(this, taskDataView)
        taskPresenter?.init()
    }

    override fun showTaskMessage(message: String) {
        text_task_message.text = message
    }

}