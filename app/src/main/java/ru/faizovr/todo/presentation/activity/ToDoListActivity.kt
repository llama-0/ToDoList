package ru.faizovr.todo.presentation.activity

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.core.widget.addTextChangedListener
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

    private val TAG = "MainActivity"

    private var taskListPresenter: TaskListContract.PresenterInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val app = application as ToDoApplication

        setupPresenter(app.model)
        setupViews()
        taskListPresenter?.init()
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyPresenter()
    }

    private fun destroyPresenter() {
        taskListPresenter = null
    }

    private fun setupPresenter(model: Model?) {
        if (model != null)
            taskListPresenter = TaskListPresenter(this, model)
        else
            throw NullPointerException()
    }

    private fun setupViews() {
        edit_text_add.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                button_addTask.isClickable = edit_text_add.text.isNotEmpty()
            }
        })

        button_addTask?.setOnClickListener {
            taskListPresenter?.addTaskToList(edit_text_add.text.toString())
            edit_text_add.text.clear()
            button_addTask.isClickable = false
        }

        button_addTask.isClickable = edit_text_add.text.toString().isNotEmpty()

        lists_recycler_view.adapter = ListRecyclerViewAdapter(taskListPresenter?.getList())
        lists_recycler_view.layoutManager = LinearLayoutManager(this)
    }


    override fun displayList(taskList: List<Task>) {
        val recyclerViewAdapter = lists_recycler_view.adapter as ListRecyclerViewAdapter
        recyclerViewAdapter.taskList = taskList
        recyclerViewAdapter.notifyDataSetChanged()

        lists_recycler_view.visibility = View.VISIBLE
        text_empty.visibility = View.GONE
    }

    override fun displayNoList() {
        lists_recycler_view.visibility = View.GONE
        text_empty.visibility = View.VISIBLE
    }
}