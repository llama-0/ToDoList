package ru.faizovr.todo.presentation.presenter

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.mockingDetails
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Test
import ru.faizovr.todo.domain.model.Model
import ru.faizovr.todo.domain.model.TaskState
import ru.faizovr.todo.presentation.InputState
import ru.faizovr.todo.presentation.contract.TaskListContract
import java.lang.reflect.Field

class PresenterTest {

    private val model: Model = mock()
    private val view: TaskListContract.View = mock()
    private val taskListPresenter = TaskListPresenter(view, model)
    private lateinit var fieldInputState: Field
    private lateinit var fieldAddTextString: Field
    private lateinit var fieldEditTextString: Field

    private fun setAddInputState() {
        fieldInputState = taskListPresenter.javaClass.getDeclaredField("inputState")
        fieldInputState.isAccessible = true
        fieldInputState.set(taskListPresenter, InputState.ADD)
    }

    private fun setEditInputState() {
        fieldInputState = taskListPresenter.javaClass.getDeclaredField("inputState")
        fieldInputState.isAccessible = true
        fieldInputState.set(taskListPresenter, InputState.EDIT)
    }

    private fun setMessageToAddTextString(message: String) {
        fieldAddTextString = taskListPresenter.javaClass.getDeclaredField("addTextString")
        fieldAddTextString.isAccessible = true
        fieldAddTextString.set(taskListPresenter, message)
    }

    private fun setMessageToEditTextString(message: String) {
        fieldEditTextString = taskListPresenter.javaClass.getDeclaredField("editTextString")
        fieldEditTextString.isAccessible = true
        fieldEditTextString.set(taskListPresenter, message)
    }

    @Test
    fun `onMainButtonClicked when inputState = Add, and verify addTask called`() {
        setAddInputState()
        setMessageToAddTextString(TEST_MESSAGE)
        taskListPresenter.onMainButtonClicked()
        verify(model).addTask(TEST_MESSAGE)
    }

    @Test
    fun `onMainButtonClicked when inputState = Add, and verify clearEditText called`() {
        setAddInputState()
        setMessageToAddTextString(TEST_MESSAGE)
        taskListPresenter.onMainButtonClicked()
        verify(view).clearEditText()
    }

    @Test
    fun `onMainButtonClicked when inputState = Edit, and verify setTaskMessage called`() {
        val pos = model.getEditableTaskPosition()

        setEditInputState()
        setMessageToEditTextString(TEST_MESSAGE)
        taskListPresenter.onMainButtonClicked()
        verify(model).setTaskMessage(pos, TEST_MESSAGE)
    }

    @Test
    fun `onMainButtonClicked when inputState = Edit, and verify setTaskState called`() {
        val pos = model.getEditableTaskPosition()

        setEditInputState()
        setMessageToEditTextString(TEST_MESSAGE)
        taskListPresenter.onMainButtonClicked()
        verify(model).setTaskState(pos, TaskState.DEFAULT)
    }

    @Test
    fun `onMainButtonClicked when inputState = Edit, and verify setToDoTaskInputText called`() {
        setEditInputState()
        setMessageToEditTextString(TEST_MESSAGE)
        taskListPresenter.onMainButtonClicked()
        verify(view).setToDoTaskInputText(TEST_MESSAGE)
    }

    companion object {
        private const val TEST_MESSAGE = "Test"
    }
}