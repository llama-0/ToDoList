package ru.faizovr.todo.presentation.presenter

import ru.faizovr.todo.domain.model.Model
import ru.faizovr.todo.domain.model.Task
import ru.faizovr.todo.domain.model.TaskState
import ru.faizovr.todo.presentation.InputState
import ru.faizovr.todo.presentation.contract.TaskListContract
import ru.faizovr.todo.presentation.mapper.TaskMapper
import ru.faizovr.todo.presentation.viewholder.TaskDataView

class TaskListPresenter(
        private val viewInterface: TaskListContract.ViewInterface,
        private val model: Model,
        private val taskMapper: TaskMapper = TaskMapper()
) : TaskListContract.PresenterInterface {

    private var addTextString: String = ""
    private var editTextString: String = ""
    private var inputState: InputState = InputState.ADD

    override fun init() {
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
            viewInterface.setToDoTaskInputText(addTextString)
        }
        deleteTask(position)
        showContent()
    }

    override fun onMainButtonClicked() {
        if (inputState == InputState.ADD) {
            model.addTask(addTextString)
            addTextString = ""
            viewInterface.clearEditText()
        } else {
            model.setTaskMessage(model.getEditableTaskPosition(), editTextString)
            model.setTaskState(model.getEditableTaskPosition(), TaskState.DEFAULT)
            viewInterface.setToDoTaskInputText(editTextString)
            inputState = InputState.ADD
        }
        showContent()
    }

    private fun setupButtonLogic() {
        val alpha: Float
        if (inputState == InputState.ADD) {
            viewInterface.setMainButtonClickable(addTextString.isNotEmpty())
            alpha = if (addTextString.isNotEmpty()) 1F else 0.5F
            viewInterface.setMainButtonAlpha(alpha)
        } else {
            viewInterface.setMainButtonClickable(editTextString.isNotEmpty())
            alpha = if (editTextString.isNotEmpty()) 1F else 0.5F
            viewInterface.setMainButtonAlpha(alpha)
        }
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
            addTextString = message
        } else {
            editTextString = message
        }
        setupButtonLogic()
    }

    override fun onSaveInstanceState() {
        model.saveDataToSharedPreference()
    }

    private fun showList(taskList: List<TaskDataView>) {
        viewInterface.setEmptyTextMessageVisibility(false)
        viewInterface.setListVisibility(true)
        viewInterface.updateList(taskList)
    }

    override fun onTaskClickedForPosition(position: Int) {
        val taskFromPosition = model.getTaskFromPosition(position)
        val message = taskFromPosition?.message ?: ""
        viewInterface.showTaskFragment(message)
    }

    private fun showEmptyListText() {
        viewInterface.setEmptyTextMessageVisibility(true)
        viewInterface.setListVisibility(false)
    }

    private fun updateList() {
        val taskList: List<TaskDataView> = model.getCopyList()
                .map(taskMapper::mapFromEntity)
        if (taskList.isNotEmpty()) {
            showList(taskList)
        } else {
            showEmptyListText()
        }
    }

    private fun setupToDoTaskInputText() {
        if (inputState == InputState.ADD) {
            viewInterface.setToDoTaskInputText(addTextString)
        } else {
            viewInterface.setToDoTaskInputText(model.getEditableTaskMessage())
        }
    }

    private fun showContent() {
        updateList()
        setupButtonLogic()
        setupToDoTaskInputText()
        changeButtonText()
    }
}