package ru.faizovr.todo.presentation.presenter

import ru.faizovr.todo.data.Model
import ru.faizovr.todo.data.Task
import ru.faizovr.todo.data.TaskState
import ru.faizovr.todo.presentation.InputState
import ru.faizovr.todo.presentation.TaskListContract

class TaskListPresenter(private val viewInterface: TaskListContract.ViewInterface, private val model: Model)
    : TaskListContract.PresenterInterface {

    private var editTextString: String = ""
    private var inputState: InputState = InputState.ADD

    override fun init() {
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

    override fun buttonAddTaskClicked(message: String) {
        model.addTask(message)
        editTextString = ""
        viewInterface.clearEditText()
        showContent()
    }

    override fun buttonEditTaskClicked(message: String) {
        model.setTaskMessage(model.getEditableTaskPosition(), message)
        model.setTaskState(model.getEditableTaskPosition(), TaskState.DEFAULT)
        viewInterface.setToDoTaskInputText(editTextString)
        inputState = InputState.ADD
        showContent()
    }

    private fun setupButtonLogic() {
        if (inputState == InputState.ADD)
            viewInterface.setAddFuncToMainButton()
        else
            viewInterface.setEditFuncToMainButton()
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
            viewInterface.setToDoTaskInputText(model.getEditableTaskMessage())
        } else {
            if (model.getEditableTaskPosition() == position) {
                inputState = InputState.ADD
                model.setTaskState(position, TaskState.DEFAULT)
                viewInterface.setToDoTaskInputText(editTextString)
            } else {
                model.setTaskState(model.getEditableTaskPosition(), TaskState.DEFAULT)
                model.setTaskState(position, TaskState.EDIT)
                viewInterface.setToDoTaskInputText(model.getEditableTaskMessage())
            }
        }
        showContent()
    }

    override fun onTaskMessageInputTextChanged(message: String) {
        if (inputState == InputState.ADD) {
            editTextString = message
        }
        viewInterface.setMainButtonClickable(message.isNotEmpty())
    }

    private fun updateList() {
        val taskList: List<Task> = model.getCopyList()
        if (taskList.isEmpty()) {
            viewInterface.setEmptyTextMessageVisibility(true)
            viewInterface.setListVisibility(false)
        } else {
            viewInterface.setEmptyTextMessageVisibility(false)
            viewInterface.setListVisibility(true)
            viewInterface.updateList(taskList)
        }
    }

    private fun showContent() {
        updateList()
        setupButtonLogic()
        viewInterface.setMainButtonClickable(editTextString.isNotEmpty())
        changeButtonText()
    }

    companion object {
        @Suppress("unused")
        private const val TAG = "MainPresenter"
    }
}