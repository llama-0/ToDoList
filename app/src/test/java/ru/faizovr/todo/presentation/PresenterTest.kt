package ru.faizovr.todo.presentation

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test
import org.mockito.Mockito.verify
import ru.faizovr.todo.domain.model.Model
import ru.faizovr.todo.domain.model.Task
import ru.faizovr.todo.domain.model.TaskState
import ru.faizovr.todo.presentation.contract.TaskListContract
import ru.faizovr.todo.presentation.presenter.TaskListPresenter

class PresenterTest {

    private val model: Model = mock()
    private val view: TaskListContract.View = mock()
    private val presenter = TaskListPresenter(view, model)

    @Test
    fun `init test when`() {
        presenter.init()

        verify(view).setFuncToMainButton()
        verify(view).setListVisibility(false)
        verify(view).setEmptyTextMessageVisibility(true)
        verify(view).setMainButtonClickable(false)
        verify(view).setAddTextToMainButton()
        verify(view).setToDoTaskInputText("")
    }

    @Test
    fun `list item moved`() {
        presenter.listItemMoved(0, 1)

        verify(model).swapTask(0, 1)
        verify(view).setListVisibility(false)
        verify(view).setEmptyTextMessageVisibility(true)
        verify(view).setMainButtonClickable(false)
        verify(view).setAddTextToMainButton()
        verify(view).setToDoTaskInputText("")
    }

    @Test
    fun `list item swiped`() {
        whenever(model.getMyList()).thenReturn(listOf(Task(0, "0"), Task(1, "1")))
        presenter.listItemSwiped(0)

        verify(model).deleteTask(0)
        verify(view).setListVisibility(false)
        verify(view).setEmptyTextMessageVisibility(true)
        verify(view).setMainButtonClickable(false)
        verify(view).setAddTextToMainButton()
        verify(view).setToDoTaskInputText("")
    }

    @Test
    fun `onMainButtonClicked when add state`() {
        presenter.onMainButtonClicked()

        verify(model).addTask("Test")
        verify(view).clearEditText()

        verify(view).setListVisibility(false)
        verify(view).setEmptyTextMessageVisibility(true)
        verify(view).setMainButtonClickable(false)
        verify(view).setAddTextToMainButton()
        verify(view).setToDoTaskInputText("")
    }

    @Test
    fun onEditTaskClickedForPosition() {
        whenever(model.getMyList()).thenReturn(listOf(Task(0, "0"), Task(1, "1")))
        presenter.onEditTaskClickedForPosition(0)
        verify(model).setTaskState(0, TaskState.EDIT)

        verify(view).setListVisibility(false)
        verify(view).setEmptyTextMessageVisibility(true)
        verify(view).setMainButtonClickable(false)
        verify(view).setEditTextToMainButton()
    }

    @Test
    fun onCheckBoxTaskClickedForPosition() {
        whenever(model.getTaskFromPosition(0)).thenReturn(Task(0, "TEST", TaskState.DEFAULT))
        whenever(model.getMyList()).thenReturn(listOf(Task(0, "0"), Task(1, "1")))

        presenter.onCheckBoxTaskClickedForPosition(0)

        verify(model).getTaskFromPosition(0)
        verify(model).setTaskState(0, TaskState.COMPLETE)
        verify(view).setListVisibility(false)
        verify(view).setEmptyTextMessageVisibility(true)
        verify(view).setMainButtonClickable(false)
        verify(view).setAddTextToMainButton()
    }
}