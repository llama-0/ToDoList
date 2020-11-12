package ru.faizovr.todo.presentation.activity

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import ru.faizovr.todo.R
import ru.faizovr.todo.ToDoApplication
import ru.faizovr.todo.data.Task
import ru.faizovr.todo.presentation.TaskListContract
import ru.faizovr.todo.presentation.adapter.ToDoTaskAdapter
import ru.faizovr.todo.presentation.presenter.TaskListPresenter
class ToDoListActivity : Activity(), TaskListContract.ViewInterface {

    private lateinit var taskListPresenter: TaskListContract.PresenterInterface

    private val onEditButtonClicked: (position: Int) -> Unit  = { it: Int ->
        taskListPresenter.onEditTaskClickedForPosition(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
        setupPresenter()
    }

    private fun setupPresenter() {
        val app: ToDoApplication = application as ToDoApplication
        taskListPresenter = TaskListPresenter(this, app.model)
        taskListPresenter.init()
    }

    private fun setupViews() {
        edit_text_add.addTextChangedListener(MyTextWatcher())

        ItemTouchHelper(ListItemTouchHelper()).attachToRecyclerView(lists_recycler_view)
        lists_recycler_view.adapter = ToDoTaskAdapter(onEditButtonClicked)

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

    override fun updateList(taskList: List<Task>) {
        val adapter: ToDoTaskAdapter = lists_recycler_view.adapter as ToDoTaskAdapter
        adapter.updateList(taskList)
    }

    companion object {
        @Suppress("unused")
        private const val TAG = "MainActivity"
    }

    inner class MyTextWatcher : TextWatcher {
        override fun afterTextChanged(s: Editable?): Unit = Unit

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int): Unit = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            taskListPresenter.onTaskMessageInputTextChanged(edit_text_add.text.toString())
        }
    }

    inner class ListItemTouchHelper : ItemTouchHelper.Callback() {

        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            val dragFlags: Int = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipeFlags: Int = ItemTouchHelper.START or ItemTouchHelper.END
            return makeMovementFlags(dragFlags, swipeFlags)
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            taskListPresenter.listItemMoved(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int): Unit =
                taskListPresenter.listItemSwiped(viewHolder.adapterPosition)

        override fun isLongPressDragEnabled(): Boolean = true

        override fun isItemViewSwipeEnabled(): Boolean = true
    }
}


