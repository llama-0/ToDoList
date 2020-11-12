package ru.faizovr.todo.presentation.activity

import android.app.Activity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import kotlinx.android.synthetic.main.activity_main.*
import ru.faizovr.todo.R
import ru.faizovr.todo.ToDoApplication
import ru.faizovr.todo.presentation.TaskListContract
import ru.faizovr.todo.presentation.adapter.ToDoTaskAdapter
import ru.faizovr.todo.presentation.presenter.TaskListPresenter
import ru.faizovr.todo.presentation.textwatcher.MessageInputTextWatcher
import ru.faizovr.todo.presentation.touchhelper.TaskTouchHelper
import ru.faizovr.todo.presentation.viewholder.TaskDataView

class ToDoListActivity : Activity(), TaskListContract.ViewInterface {

    private lateinit var taskListPresenter: TaskListContract.PresenterInterface

    private val onEditButtonClicked: (position: Int) -> Unit = { it: Int ->
        taskListPresenter.onEditTaskClickedForPosition(it)
    }

    private val onCheckBoxClicked: (position: Int) -> Unit = { it: Int ->
        taskListPresenter.onCheckBoxTaskClickedForPosition(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
        setupPresenter()
        setupHelpers()
    }

    private fun setupPresenter() {
        val app: ToDoApplication = application as ToDoApplication
        taskListPresenter = TaskListPresenter(viewInterface = this, model = app.model)
        taskListPresenter.init()
    }

    private fun setupViews() {
        lists_recycler_view.adapter = ToDoTaskAdapter(onEditButtonClicked, onCheckBoxClicked)
    }

    private fun setupHelpers() {
        edit_text_add.addTextChangedListener(MessageInputTextWatcher(taskListPresenter))
        ItemTouchHelper(TaskTouchHelper(taskListPresenter)).attachToRecyclerView(lists_recycler_view)
    }

    override fun setAddFuncToMainButton() {
        button_main.setOnClickListener {
            taskListPresenter.buttonAddTaskClicked(edit_text_add.text.toString())
        }
    }

    override fun setEditFuncToMainButton() {
        button_main.setOnClickListener {
            taskListPresenter.buttonEditTaskClicked(edit_text_add.text.toString())
        }
    }

    override fun clearEditText() {
        edit_text_add.text.clear()
    }

    override fun setMainButtonClickable(isClickable: Boolean) {
        button_main.isClickable = isClickable
    }

    override fun setListVisibility(isVisible: Boolean) {
        lists_recycler_view.isVisible = isVisible
    }

    override fun setEmptyTextMessageVisibility(isVisible: Boolean) {
        text_empty.isVisible = isVisible
    }

    override fun setToDoTaskInputText(message: String) {
        edit_text_add.setText(message)
    }

    override fun setAddTextToMainButton() {
        button_main.setText(R.string.action_add_task)
    }

    override fun setEditTextToMainButton() {
        button_main.setText(R.string.action_edit_task)
    }

    override fun updateList(taskList: List<TaskDataView>) {
        val adapter: ToDoTaskAdapter = lists_recycler_view.adapter as ToDoTaskAdapter
        adapter.updateList(taskList)
    }

    companion object {
        @Suppress("unused")
        private const val TAG = "MainActivity"
    }
}


