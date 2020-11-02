package ru.faizovr.todo.presentation.presenter

import ru.faizovr.todo.data.Model
import ru.faizovr.todo.data.Task
import ru.faizovr.todo.data.TaskState
import ru.faizovr.todo.presentation.InputState
import ru.faizovr.todo.presentation.TaskListContract

class TaskListPresenter (private val viewInterface: TaskListContract.ViewInterface, private val model: Model)
    : TaskListContract.PresenterInterface {

    private var editTextString: String = ""
    private var editTextStringAdd: String = ""
    private var inputState: InputState = InputState.ADD
    private lateinit var editableTask: Task
    override fun init() {
        showContent()
    }

    private fun deleteTask(task: Task) {
        if (model.isContain(task)) {
            model.deleteTask(task)
        }
    }

    override fun listItemSwiped(task: Task) {
        deleteTask(task)
        viewInterface.changeButtonClickable(editTextString.isNotEmpty())
        showContent()
    }

    // Подумать как исправить эти 2 костыля

    override fun buttonAddTaskClicked(message: String) {
        if (inputState == InputState.ADD) {
            model.addTask(Task(message))
            editTextString = ""
            viewInterface.clearEditText()
            viewInterface.changeButtonClickable(false)
        } else {
            editableTask.message = message
            viewInterface.changeAddButtonText("Add")
            inputState = InputState.ADD
            model.changeTaskState(editableTask, TaskState.SHOW)
            viewInterface.changeEditTextText(editTextStringAdd)
        }
        showContent()
    }

    override fun buttonEditTaskClicked(task: Task, saveString: String) {
        if (inputState == InputState.ADD) {
            editTextStringAdd = saveString
            viewInterface.changeAddButtonText("Edit")
            inputState = InputState.EDIT
            viewInterface.changeEditTextText(task.message)
            model.changeTaskState(task, TaskState.EDIT)
            editableTask = task

        } else {
            if (editableTask == task) {
                viewInterface.changeAddButtonText("Add")
                inputState = InputState.ADD
                model.changeTaskState(task, TaskState.SHOW)
                viewInterface.changeEditTextText(editTextStringAdd)
            } else {
                viewInterface.changeEditTextText(task.message)
                model.changeTaskState(editableTask, TaskState.SHOW)
                model.changeTaskState(task, TaskState.EDIT)
                editableTask = task
            }
        }
        showContent()

    }

    override fun editTextTextChanged(string: String) {
        editTextString = string
        viewInterface.changeButtonClickable(string.isNotEmpty())
    }

    private fun showContent() {
        val taskList = model.getMyList()
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

    companion object {
        @Suppress("unused")
        private const val TAG = "MainPresenter"
    }
}