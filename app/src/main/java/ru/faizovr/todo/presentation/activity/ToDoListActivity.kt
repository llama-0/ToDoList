package ru.faizovr.todo.presentation.activity

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import ru.faizovr.todo.ToDoApplication
import ru.faizovr.todo.R
import ru.faizovr.todo.presentation.adapter.ListRecyclerViewAdapter
import ru.faizovr.todo.data.Task
import ru.faizovr.todo.presentation.TaskListContract
import ru.faizovr.todo.presentation.presenter.TaskListPresenter

class ToDoListActivity : Activity(), TaskListContract.ViewInterface {

    private lateinit var taskListPresenter: TaskListContract.PresenterInterface

    private val onEditButtonClicked : (task: Task) -> Unit = {
        it.message = "Text Changed"
        taskListPresenter.init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
        setupPresenter()
        lists_recycler_view.adapter = ListRecyclerViewAdapter(onEditButtonClicked)
    }

    private fun setupPresenter() {
        val app = application as ToDoApplication
        taskListPresenter = TaskListPresenter(this, app.model)
        taskListPresenter.init()
    }

    private fun setupViews() {
        edit_text_add.addTextChangedListener(MyTextWatcher())

        button_addTask.setOnClickListener {
            taskListPresenter.buttonAddTaskClicked(edit_text_add.text.toString())
        }

        ItemTouchHelper(ListItemTouchHelper()).attachToRecyclerView(lists_recycler_view)

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

    override fun changeEditTextText(message: String) {
        edit_text_add.setText(message)
    }

    override fun changeAddButtonText(message: String) {
        button_addTask.text = message
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
            taskListPresenter.editTextTextChanged(s.toString())
        }
    }

    inner class ListItemTouchHelper() : ItemTouchHelper.Callback() {

        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
            return makeMovementFlags(dragFlags, swipeFlags)
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            val listRecyclerViewAdapter = lists_recycler_view.adapter as ListRecyclerViewAdapter
            taskListPresenter.listItemMoved(listRecyclerViewAdapter.getTaskFromView(viewHolder.adapterPosition), target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val listRecyclerViewAdapter = lists_recycler_view.adapter as ListRecyclerViewAdapter
            taskListPresenter.listItemSwiped(listRecyclerViewAdapter.getTaskFromView(viewHolder.adapterPosition))
        }

        override fun isLongPressDragEnabled(): Boolean {
            return true
        }

        override fun isItemViewSwipeEnabled(): Boolean {
            return true
        }
    }
}

