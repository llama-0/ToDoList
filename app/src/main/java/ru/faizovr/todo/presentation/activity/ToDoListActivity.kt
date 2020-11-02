package ru.faizovr.todo.presentation.activity

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import ru.faizovr.todo.ToDoApplication
import ru.faizovr.todo.R
import ru.faizovr.todo.data.Model
import ru.faizovr.todo.presentation.adapter.ListRecyclerViewAdapter
import ru.faizovr.todo.data.Task
import ru.faizovr.todo.presentation.TaskListContract
import ru.faizovr.todo.presentation.presenter.TaskListPresenter

class ToDoListActivity : Activity(), TaskListContract.ViewInterface {

    private var taskListPresenter: TaskListContract.PresenterInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val app = application as ToDoApplication

        setupViews()
        setupPresenter(app.model)
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyPresenter()
    }

    private fun destroyPresenter() {
        taskListPresenter = null
    }

    private fun setupPresenter(model: Model) {
        taskListPresenter = TaskListPresenter(this, model)
        taskListPresenter?.init()
    }

    private fun setupViews() {

        edit_text_add.addTextChangedListener(MyTextWatcher())

        button_addTask?.setOnClickListener {
            taskListPresenter?.buttonAddTaskClicked(edit_text_add.text.toString())
        }

        ItemTouchHelper(ItemListTouchHelper()).attachToRecyclerView(lists_recycler_view)

        lists_recycler_view.adapter = ListRecyclerViewAdapter()
        lists_recycler_view.layoutManager = LinearLayoutManager(this)
    }

    override fun clearEditText() {
        edit_text_add.text.clear()
    }

    override fun changeButtonClickable(isClickable: Boolean) {
        button_addTask.isClickable = isClickable
    }

    override fun changeListVisibility(isVisible: Boolean) {
        lists_recycler_view.visibility = when (isVisible) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

    override fun changeEmptyTextMessageVisibility(isVisible: Boolean) {
        text_empty.visibility = when (isVisible) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

    override fun updateList(taskList: List<Task>) {
        val adapter: ListRecyclerViewAdapter = lists_recycler_view.adapter as ListRecyclerViewAdapter
        adapter.updateList(taskList)
    }

    companion object {
        @Suppress("unused")
        private const val TAG = "MainActivity"
    }

    inner class MyTextWatcher : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            taskListPresenter?.textChanged(s.toString())
        }
    }

    inner class ItemListTouchHelper: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
        override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            taskListPresenter?.listItemSwipped(viewHolder.adapterPosition)
        }

    }
}

