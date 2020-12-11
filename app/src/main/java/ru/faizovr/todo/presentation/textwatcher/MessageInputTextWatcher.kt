package ru.faizovr.todo.presentation.textwatcher

import android.text.Editable
import android.text.TextWatcher
import ru.faizovr.todo.presentation.contract.TaskListContract

class MessageInputTextWatcher(private val taskListPresenter: TaskListContract.Presenter) :
    TextWatcher {
    override fun afterTextChanged(s: Editable?): Unit = Unit

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int): Unit =
        Unit

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        taskListPresenter.onTaskMessageInputTextChanged(s.toString())
    }
}