package ru.faizovr.todo.presentation.presenter

import android.util.Log
import ru.faizovr.todo.domain.model.Model
import ru.faizovr.todo.domain.model.Task
import ru.faizovr.todo.domain.model.TaskState
import ru.faizovr.todo.presentation.InputState
import ru.faizovr.todo.presentation.TaskListContract
import ru.faizovr.todo.presentation.mapper.TaskMapper
import ru.faizovr.todo.presentation.viewholder.TaskDataView

class TaskListPresenter(private val viewInterface: TaskListContract.ViewInterface, private val model: Model)
    : TaskListContract.PresenterInterface {

    private var editTextString: String = ""
    private var inputState: InputState = InputState.ADD

    override fun init() {
        Log.d(TAG, "init: ")
        viewInterface.setFuncToMainButton()
        setupInputState()
        showContent()
    }

    private fun setupInputState() {
        val task = model.getMyList().find { it.taskState == TaskState.EDIT }
        if (task != null) {
            inputState = InputState.EDIT
        }
    }

    private fun deleteTask(position: Int) {
        Log.d(TAG, "deleteTask: ")
        if (model.getMyList().size > position) {
            model.deleteTask(position)
        }
    }

    override fun listItemMoved(fromPosition: Int, toPosition: Int) {
        Log.d(TAG, "listItemMoved: ")
        model.swapTask(fromPosition, toPosition)
        showContent()
    }

    override fun listItemSwiped(position: Int) {
        Log.d(TAG, "listItemSwiped: ")
        if (inputState == InputState.EDIT && position == model.getEditableTaskPosition()) {
            inputState = InputState.ADD
            viewInterface.setToDoTaskInputText(editTextString)
        }
        deleteTask(position)
        showContent()
    }

    override fun onMainButtonClicked(message: String) {
        Log.d(TAG, "onMainButtonClicked: ")
        if (inputState == InputState.ADD) {
            model.addTask(message)
            editTextString = ""
            viewInterface.clearEditText()
        } else {
            model.setTaskMessage(model.getEditableTaskPosition(), message)
            model.setTaskState(model.getEditableTaskPosition(), TaskState.DEFAULT)
            viewInterface.setToDoTaskInputText(editTextString)
            inputState = InputState.ADD
        }
        showContent()
    }

    private fun setupButtonLogic() {
        Log.d(TAG, "setupButtonLogic: ")
        viewInterface.setMainButtonClickable(editTextString.isNotEmpty())
    }

    private fun changeButtonText() {
        Log.d(TAG, "changeButtonText: ")
        if (inputState == InputState.ADD)
            viewInterface.setAddTextToMainButton()
        else
            viewInterface.setEditTextToMainButton()
    }

    override fun onEditTaskClickedForPosition(position: Int) {
        Log.d(TAG, "onEditTaskClickedForPosition: ")
        if (inputState == InputState.ADD) {
            inputState = InputState.EDIT
            model.setTaskState(position, TaskState.EDIT)
        } else {
            if (model.getTaskFromPosition(position)?.taskState == TaskState.EDIT) {
                inputState = InputState.ADD
                model.setTaskState(position, TaskState.DEFAULT)
            } else {
                model.setTaskState(model.getEditableTaskPosition(), TaskState.DEFAULT)
                model.setTaskState(position, TaskState.EDIT)
            }
        }
        showContent()
    }

    override fun onCheckBoxTaskClickedForPosition(position: Int) {
        Log.d(TAG, "onCheckBoxTaskClickedForPosition: ")
        val taskAtPosition: Task? = model.getTaskFromPosition(position)
        if (taskAtPosition != null) {
            when (taskAtPosition.taskState) {
                TaskState.DEFAULT -> {
                    model.setTaskState(position, TaskState.COMPLETE)
                }
                TaskState.EDIT -> {
                    inputState = InputState.ADD
                    model.setTaskState(position, TaskState.COMPLETE)
                }
                TaskState.COMPLETE -> {
                    model.setTaskState(position, TaskState.DEFAULT)
                }
            }
            showContent()
        }
    }

    override fun onTaskMessageInputTextChanged(message: String) {
        Log.d(TAG, "onTaskMessageInputTextChanged: ")
        if (inputState == InputState.ADD) {
            editTextString = message
        }
        viewInterface.setMainButtonClickable(message.isNotEmpty())
    }

    override fun onSaveInstanceState() {
        Log.d(TAG, "onSaveInstanceState: ")
        model.setDataToSharedPreference()
    }

    private fun showList(taskList: List<TaskDataView>) {
        Log.d(TAG, "showList: ")
        viewInterface.setEmptyTextMessageVisibility(false)
        viewInterface.setListVisibility(true)
        viewInterface.updateList(taskList)
    }

    private fun showText() {
        Log.d(TAG, "showText: ")
        viewInterface.setEmptyTextMessageVisibility(true)
        viewInterface.setListVisibility(false)
    }

    private fun updateList() {
        Log.d(TAG, "updateList: ")
        val taskMapper = TaskMapper()
        val taskList: List<TaskDataView> = model.getCopyList().map { taskMapper.mapFromEntity(it) }.toList()
        if (taskList.isNotEmpty()) {
            showList(taskList)
        } else {
            showText()
        }
    }

    private fun setupToDoTaskInputText() {
        Log.d(TAG, "setupToDoTaskInputText: ")
        if (inputState == InputState.ADD) {
            viewInterface.setToDoTaskInputText(editTextString)
        } else {
            viewInterface.setToDoTaskInputText(model.getEditableTaskMessage())
        }
    }

    private fun showContent() {
        Log.d(TAG, "showContent: ")
        updateList()
        setupButtonLogic()
        viewInterface.setMainButtonClickable(editTextString.isNotEmpty())
        setupToDoTaskInputText()
        changeButtonText()
    }

    companion object {
        private const val TAG = "TaskListPresenter"
    }
}