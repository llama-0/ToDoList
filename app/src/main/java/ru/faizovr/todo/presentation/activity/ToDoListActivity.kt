package ru.faizovr.todo.presentation.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private var recyclerViewAdapter: ListRecyclerViewAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var editTextAdd: EditText? = null
    private var buttonAdd: Button? = null
    private var emptyTextView: TextView? = null

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
        destroyViews()
        destroyPresenter()
    }

    private fun destroyViews() {
        editTextAdd = null
        recyclerViewAdapter = null
        recyclerView?.layoutManager = null
        recyclerView = null
        buttonAdd = null
        emptyTextView = null
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
        editTextAdd = findViewById(R.id.edit_text_add)
        buttonAdd = findViewById(R.id.button_addTask)
        emptyTextView = findViewById(R.id.text_empty)
        recyclerView = findViewById(R.id.lists_recycler_view)

        buttonAdd?.isClickable = editTextAdd?.text!!.isNotEmpty()
        editTextAdd?.addTextChangedListener {
            buttonAdd?.isClickable = editTextAdd?.text!!.isNotEmpty()
        }

        buttonAdd?.setOnClickListener {
            taskListPresenter?.addTaskToList(editTextAdd?.text.toString())
            editTextAdd?.text!!.clear()
        }

        recyclerViewAdapter =
            ListRecyclerViewAdapter(
                taskListPresenter?.getList()!!
            )
        recyclerView?.adapter = recyclerViewAdapter
        recyclerView?.layoutManager = LinearLayoutManager(this)
    }

    override fun displayList(taskList: List<Task>) {
        recyclerViewAdapter?.taskList = taskList
        recyclerViewAdapter?.notifyDataSetChanged()

        recyclerView?.visibility = View.VISIBLE
        emptyTextView?.visibility = View.GONE
    }

    override fun displayNoList() {
        recyclerView?.visibility = View.GONE
        emptyTextView?.visibility = View.VISIBLE
    }
}