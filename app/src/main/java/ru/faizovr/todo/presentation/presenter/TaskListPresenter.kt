package ru.faizovr.todo.presentation.presenter

import ru.faizovr.todo.data.Model
import ru.faizovr.todo.data.Task
import ru.faizovr.todo.data.TaskState
import ru.faizovr.todo.presentation.InputState
import ru.faizovr.todo.presentation.TaskListContract
import ru.faizovr.todo.presentation.mapper.TaskMapper
import ru.faizovr.todo.presentation.viewholder.TaskDataView

class TaskListPresenter(private val viewInterface: TaskListContract.ViewInterface, private val model: Model)
    : TaskListContract.PresenterInterface {

    private var editTextString: String = ""
    private var inputState: InputState = InputState.ADD

    override fun init() {
        viewInterface.setFuncToMainButton()
        showContent()
    }

    private fun deleteTask(position: Int) {
        if (model.getMyList().size > position) {
            model.deleteTask(position)
        }
    }

    override fun listItemMoved(fromPosition: Int, toPosition: Int) {
        model.swapTask(fromPosition, toPosition)
        showContent()
    }

    override fun listItemSwiped(position: Int) {
        if (inputState == InputState.EDIT && position == model.getEditableTaskPosition()) {
            inputState = InputState.ADD
            viewInterface.setToDoTaskInputText(editTextString)
        }
        deleteTask(position)
        showContent()
    }

    override fun onMainButtonClicked(message: String) {
        if (inputState == InputState.ADD) {
            model.addTask(message)
            editTextString = ""
            viewInterface.clearEditText()
        } else  {
            model.setTaskMessage(model.getEditableTaskPosition(), message)
            model.setTaskState(model.getEditableTaskPosition(), TaskState.DEFAULT)
            viewInterface.setToDoTaskInputText(editTextString)
            inputState = InputState.ADD
        }
        showContent()
    }

    private fun setupButtonLogic() {
        viewInterface.setMainButtonClickable(editTextString.isNotEmpty())
    }

    private fun changeButtonText() {
        if (inputState == InputState.ADD)
            viewInterface.setAddTextToMainButton()
        else
            viewInterface.setEditTextToMainButton()
    }

    override fun onEditTaskClickedForPosition(position: Int) {
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
        if (inputState == InputState.ADD) {
            editTextString = message
        }
        viewInterface.setMainButtonClickable(message.isNotEmpty())
    }

    private fun showList(taskList: List<TaskDataView>) {
        viewInterface.setEmptyTextMessageVisibility(false)
        viewInterface.setListVisibility(true)
        viewInterface.updateList(taskList)
    }

    private fun showText() {
        viewInterface.setEmptyTextMessageVisibility(true)
        viewInterface.setListVisibility(false)
    }

    private fun updateList() {
        val taskMapper = TaskMapper()
        val taskList: List<TaskDataView> = model.getCopyList().map { taskMapper.mapFromEntity(it) }.toList()
        if (taskList.isNotEmpty()) {
            showList(taskList)
        } else {
            showText()
        }
    }

    private fun setupToDoTaskInputText() {
        if (inputState == InputState.ADD) {
            viewInterface.setToDoTaskInputText(editTextString)
        } else {
            viewInterface.setToDoTaskInputText(model.getEditableTaskMessage())
        }
    }

    private fun showContent() {
        updateList()
        setupButtonLogic()
        viewInterface.setMainButtonClickable(editTextString.isNotEmpty())
        setupToDoTaskInputText()
        changeButtonText()
    }
}