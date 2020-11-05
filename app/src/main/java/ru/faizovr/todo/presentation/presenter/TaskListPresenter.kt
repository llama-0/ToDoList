package ru.faizovr.todo.presentation.presenter

import ru.faizovr.todo.data.Model
import ru.faizovr.todo.data.Task
import ru.faizovr.todo.data.TaskState
import ru.faizovr.todo.presentation.InputState
import ru.faizovr.todo.presentation.TaskListContract

class TaskListPresenter (private val viewInterface: TaskListContract.ViewInterface, private val model: Model)
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
            viewInterface.changeEditTextText(editTextString)
        }
        deleteTask(position)
        showContent()
    }

    override fun buttonAddTaskClicked(string: String) {
        model.addTask(string)
        editTextString = ""
        viewInterface.clearEditText()
        showContent()
    }

    override fun buttonEditTaskClicked(string: String) {
        model.setTaskMessage(model.getEditableTaskPosition(), string)
        model.setTaskState(model.getEditableTaskPosition(), TaskState.DEFAULT)
        viewInterface.changeEditTextText(editTextString)
        inputState = InputState.ADD
        showContent()
    }

    private fun setupButtonLogic() {
        if (inputState == InputState.ADD)
            viewInterface.setupAddButton()
        else
            viewInterface.setupEditButton()
        viewInterface.changeButtonClickable(editTextString.isNotEmpty())
    }

    private fun changeButtonText() {
        if (inputState == InputState.ADD)
            viewInterface.setAddTextToButton()
        else
            viewInterface.setEditTextToButton()
    }

    override fun buttonListEditTaskClicked(position: Int) {
        if (inputState == InputState.ADD) {
            inputState = InputState.EDIT
            model.setTaskState(position, TaskState.EDIT)
            viewInterface.changeEditTextText(model.getEditableTaskMessage())
        } else {
            if (model.getEditableTaskPosition() == position) {
                inputState = InputState.ADD
                model.setTaskState(position, TaskState.DEFAULT)
                viewInterface.changeEditTextText(editTextString)
            } else {
                model.setTaskState(model.getEditableTaskPosition(), TaskState.DEFAULT)
                model.setTaskState(position, TaskState.EDIT)
                viewInterface.changeEditTextText(model.getEditableTaskMessage())
            }
        }
        showContent()
    }

    override fun editTextTextChanged(string: String) {
        if (inputState == InputState.ADD) {
            editTextString = string
        }
        viewInterface.changeButtonClickable(string.isNotEmpty())
    }

    private fun updateList() {
        val taskList: List<Task> = model.getMyList()
        if (taskList.isEmpty()) {
            viewInterface.changeEmptyTextMessageVisibility(true)
            viewInterface.changeListVisibility(false)
        }
        else {
            viewInterface.changeEmptyTextMessageVisibility(false)
            viewInterface.changeListVisibility(true)
            viewInterface.updateList(taskList)
        }
    }

    private fun showContent() {
        updateList()
        setupButtonLogic()
        viewInterface.changeButtonClickable(editTextString.isNotEmpty())
        changeButtonText()
    }

    companion object {
        @Suppress("unused")
        private const val TAG = "MainPresenter"
    }
}