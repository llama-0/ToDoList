package ru.faizovr.todo.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.faizovr.todo.R
import ru.faizovr.todo.adapter.ListRecyclerViewAdapter
import ru.faizovr.todo.model.Task
import ru.faizovr.todo.mvp.MainContract
import ru.faizovr.todo.presenter.MainPresenter

class MainActivity : AppCompatActivity(), MainContract.ViewInterface {

    private val TAG = "MainActivity"

    private lateinit var mainPresenter: MainContract.PresenterInterface
    private lateinit var recyclerViewAdapter: ListRecyclerViewAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var editTextAdd: EditText
    private lateinit var buttonAdd: Button
    private lateinit var emptyTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupPresenter()
        setupViews()
    }

    override fun onStart() {
        super.onStart()
        mainPresenter.showList()
    }

    private fun setupPresenter() {
        mainPresenter = MainPresenter(this, mutableListOf())
    }

    private fun setupViews() {
        editTextAdd = findViewById(R.id.edit_text_add)
        buttonAdd = findViewById(R.id.button_addTask)
        emptyTextView = findViewById(R.id.text_empty)
        buttonAdd.isClickable = editTextAdd.text.isNotEmpty()
        editTextAdd.addTextChangedListener {
            buttonAdd.isClickable = editTextAdd.text.isNotEmpty()
        }
        buttonAdd.setOnClickListener {
            mainPresenter.addTaskToList(editTextAdd.text.toString())
            editTextAdd.text.clear()
        }
        recyclerViewAdapter = ListRecyclerViewAdapter(mainPresenter.getMyList())
        recyclerView = findViewById(R.id.lists_recycler_view)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun displayList() {
        recyclerViewAdapter.taskList = mainPresenter.getMyList()
        recyclerViewAdapter.notifyDataSetChanged()

        recyclerView.visibility = View.VISIBLE
        emptyTextView.visibility = View.GONE
    }

    override fun displayNoList() {
        recyclerView.visibility = View.GONE
        emptyTextView.visibility = View.VISIBLE
    }
}