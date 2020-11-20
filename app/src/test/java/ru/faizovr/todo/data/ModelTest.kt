package ru.faizovr.todo.data


import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.faizovr.todo.domain.model.Model
import ru.faizovr.todo.domain.model.Task
import ru.faizovr.todo.domain.model.TaskState

class ModelTest {

    private val model = Model(mock())

    companion object {
        private const val TEST_MESSAGE = "Read Book"
    }

    @Before
    fun before() {
        model.addTask("test 1")
        model.addTask("test 2")
        model.addTask("test 3")
    }

    @Test
    fun `AddTask new item added`() {
        val myList: List<Task> = model.getMyList()
        val initialSize = myList.size
        val expectedSize = initialSize + 1
        val expectedMessage = TEST_MESSAGE

        model.addTask(TEST_MESSAGE)
        val lastItem: Task = myList.last()
        Assert.assertEquals(myList.size, expectedSize)
        Assert.assertEquals(lastItem.message, expectedMessage)
    }

    @Test
    fun `DeleteTask item deleted `() {
        val myList: List<Task> = model.getMyList()
        val initialSize = myList.size
        val expectedSize = initialSize - 1
        val deletedItem = myList[0]

        model.deleteTask(0)
        Assert.assertEquals(myList.size, expectedSize)
        Assert.assertEquals(myList.contains(deletedItem), false)
    }

    @Test
    fun `DeleteTask with invalid params`() {
        val myList: List<Task> = model.getMyList()
        val initialSize = myList.size
        val expectedSize = initialSize
        val expectedList: List<Task> = model.getMyList()
        val invalidElementPosition = myList.size

        model.deleteTask(invalidElementPosition)
        Assert.assertEquals(myList.size, expectedSize)
        Assert.assertEquals(myList, expectedList)
    }

    @Test
    fun `DeleteTask delete editable task, and check editable position`() {
        val myList: List<Task> = model.getMyList()
        val initialSize = myList.size
        val expectedSize = initialSize - 1
        val deletedItem = myList[0]
        val initialEditablePosition = 0
        val expectedEditablePosition = -1
        model.setTaskState(0, TaskState.EDIT)
        model.deleteTask(0)
        val actualEditablePosition = model.getEditableTaskPosition()

        Assert.assertNotEquals(initialEditablePosition, actualEditablePosition)
        Assert.assertEquals(actualEditablePosition, expectedEditablePosition)
        Assert.assertEquals(myList.size, expectedSize)
        Assert.assertEquals(myList.contains(deletedItem), false)
    }

    @Test
    fun `SwapTask with two valid params`() {
        val myList: List<Task> = model.getMyList()
        val initialSize = myList.size
        val expectedSize = initialSize
        val firstItemPosition = 0
        val secondItemPosition = 1
        val expectedItemAtFirstPosition = myList[secondItemPosition]
        val expectedItemAtSecondPosition = myList[firstItemPosition]

        model.swapTask(firstItemPosition, secondItemPosition)
        Assert.assertEquals(myList[firstItemPosition], expectedItemAtFirstPosition)
        Assert.assertEquals(myList[secondItemPosition], expectedItemAtSecondPosition)
        Assert.assertEquals(myList.size, expectedSize)
    }

    @Test
    fun `SwapTask with first invalid param and second valid param`() {
        val myList: List<Task> = model.getMyList()
        val initialSize = myList.size
        val expectedSize = initialSize
        val invalidItemPosition = myList.size
        val firstItemPosition = 0
        val secondItemPosition = 1
        val expectedItemAtFirstPosition = myList[firstItemPosition]
        val expectedItemAtSecondPosition = myList[secondItemPosition]

        model.swapTask(invalidItemPosition, secondItemPosition)

        Assert.assertEquals(myList[firstItemPosition], expectedItemAtFirstPosition)
        Assert.assertEquals(myList[secondItemPosition], expectedItemAtSecondPosition)
        Assert.assertEquals(myList.size, expectedSize)
    }

    @Test
    fun `SwapTask with first valid param and second invalid param`() {
        val myList: List<Task> = model.getMyList()
        val initialSize = myList.size
        val expectedSize = initialSize
        val invalidItemPosition = myList.size
        val firstItemPosition = 0
        val secondItemPosition = 1
        val expectedItemAtFirstPosition = myList[firstItemPosition]
        val expectedItemAtSecondPosition = myList[secondItemPosition]

        model.swapTask(firstItemPosition, invalidItemPosition)

        Assert.assertEquals(myList[firstItemPosition], expectedItemAtFirstPosition)
        Assert.assertEquals(myList[secondItemPosition], expectedItemAtSecondPosition)
        Assert.assertEquals(myList.size, expectedSize)
    }

    @Test
    fun `SwapTask with both invalid params`() {
        val myList: List<Task> = model.getMyList()
        val initialSize = myList.size
        val expectedSize = initialSize
        val firstInvalidItemPosition = myList.size
        val secondInvalidPosition = -1
        val firstItemPosition = 0
        val secondItemPosition = 1
        val expectedItemAtFirstPosition = myList[firstItemPosition]
        val expectedItemAtSecondPosition = myList[secondItemPosition]

        model.swapTask(firstInvalidItemPosition, secondInvalidPosition)

        Assert.assertEquals(myList[firstItemPosition], expectedItemAtFirstPosition)
        Assert.assertEquals(myList[secondItemPosition], expectedItemAtSecondPosition)
        Assert.assertEquals(myList.size, expectedSize)
    }

    @Test
    fun `getEditableTaskPosition when list contain editable element`() {
        val expectedTaskPosition = 0
        model.setTaskState(0, TaskState.EDIT)
        val editableTaskPosition = model.getEditableTaskPosition()

        Assert.assertEquals(editableTaskPosition, expectedTaskPosition)
    }

    @Test
    fun `getEditableTaskPosition when list doesn't contain editable element`() {
        val expectedTaskPosition = -1
        model.setTaskState(0, TaskState.DEFAULT)
        val editableTaskPosition = model.getEditableTaskPosition()

        Assert.assertEquals(editableTaskPosition, expectedTaskPosition)
    }

    @Test
    fun `getEditableTaskMessage when list contain editable message`() {
        val myList: List<Task> = model.getMyList()
        val expectedTaskMessage = myList[0].message
        model.setTaskState(0, TaskState.EDIT)
        val editableTaskMessage = model.getEditableTaskMessage()

        Assert.assertEquals(editableTaskMessage, expectedTaskMessage)
    }

    @Test
    fun `getEditableTaskMessage when list doesn't contain editable element`() {
        val expectedTaskMessage = ""
        model.setTaskState(0, TaskState.DEFAULT)
        val editableTaskMessage = model.getEditableTaskMessage()

        Assert.assertEquals(editableTaskMessage, expectedTaskMessage)
    }

    @Test
    fun `setTaskState from EDIT to EDIT`() {
        val myList: List<Task> = model.getMyList()
        val testingTaskPosition = 0
        val task = myList[testingTaskPosition]
        task.taskState = TaskState.EDIT
        val expectedTaskState = TaskState.EDIT

        model.setTaskState(testingTaskPosition, expectedTaskState)

        val actualTaskState = task.taskState
        Assert.assertEquals(actualTaskState, expectedTaskState)
    }


    @Test
    fun `setTaskState from EDIT to COMPLETE`() {
        val myList: List<Task> = model.getMyList()
        val testingTaskPosition = 0
        val task = myList[testingTaskPosition]
        task.taskState = TaskState.EDIT
        val expectedTaskState = TaskState.COMPLETE

        model.setTaskState(testingTaskPosition, expectedTaskState)

        val actualTaskState = task.taskState
        Assert.assertEquals(actualTaskState, expectedTaskState)
    }

    @Test
    fun `setTaskState from EDIT to DEFAULT`() {
        val myList: List<Task> = model.getMyList()
        val testingTaskPosition = 0
        val task = myList[testingTaskPosition]
        task.taskState = TaskState.EDIT
        val expectedTaskState = TaskState.DEFAULT

        model.setTaskState(testingTaskPosition, expectedTaskState)

        val actualTaskState = task.taskState
        Assert.assertEquals(actualTaskState, expectedTaskState)
    }

    @Test
    fun `setTaskState from DEFAULT to DEFAULT`() {
        val myList: List<Task> = model.getMyList()
        val testingTaskPosition = 0
        val task = myList[testingTaskPosition]
        task.taskState = TaskState.DEFAULT
        val expectedTaskState = TaskState.DEFAULT

        model.setTaskState(testingTaskPosition, expectedTaskState)

        val actualTaskState = task.taskState
        Assert.assertEquals(actualTaskState, expectedTaskState)
    }

    @Test
    fun `setTaskState from DEFAULT to EDIT`() {
        val myList: List<Task> = model.getMyList()
        val testingTaskPosition = 0
        val task = myList[testingTaskPosition]
        task.taskState = TaskState.DEFAULT
        val expectedTaskState = TaskState.EDIT

        model.setTaskState(testingTaskPosition, expectedTaskState)

        val actualTaskState = task.taskState
        Assert.assertEquals(actualTaskState, expectedTaskState)
    }

    @Test
    fun `setTaskState from DEFAULT to COMPLETE`() {
        val myList: List<Task> = model.getMyList()
        val testingTaskPosition = 0
        val task = myList[testingTaskPosition]
        task.taskState = TaskState.DEFAULT
        val expectedTaskState = TaskState.COMPLETE

        model.setTaskState(testingTaskPosition, expectedTaskState)

        val actualTaskState = task.taskState
        Assert.assertEquals(actualTaskState, expectedTaskState)
    }

    @Test
    fun `setTaskState from COMPLETE to COMPLETE`() {
        val myList: List<Task> = model.getMyList()
        val testingTaskPosition = 0
        val task = myList[testingTaskPosition]
        task.taskState = TaskState.COMPLETE
        val expectedTaskState = TaskState.COMPLETE

        model.setTaskState(testingTaskPosition, expectedTaskState)

        val actualTaskState = task.taskState
        Assert.assertEquals(actualTaskState, expectedTaskState)
    }

    @Test
    fun `setTaskState from COMPLETE to DEFAULT`() {
        val myList: List<Task> = model.getMyList()
        val testingTaskPosition = 0
        val task = myList[testingTaskPosition]
        task.taskState = TaskState.COMPLETE
        val expectedTaskState = TaskState.DEFAULT

        model.setTaskState(testingTaskPosition, expectedTaskState)

        val actualTaskState = task.taskState
        Assert.assertEquals(actualTaskState, expectedTaskState)
    }

    @Test
    fun `setTaskState from COMPLETE to EDIT`() {
        val myList: List<Task> = model.getMyList()
        val testingTaskPosition = 0
        val task = myList[testingTaskPosition]
        task.taskState = TaskState.COMPLETE
        val expectedTaskState = TaskState.EDIT

        model.setTaskState(testingTaskPosition, expectedTaskState)

        val actualTaskState = task.taskState
        Assert.assertEquals(actualTaskState, expectedTaskState)
    }

    @Test
    fun `testSetTaskState from SOME to EDIT test that editable position changed`() {
        val initialTaskState = TaskState.DEFAULT
        val taskPosition = 0
        model.setTaskState(taskPosition, initialTaskState)
        val initialEditablePosition = model.getEditableTaskPosition()
        val editableTaskState = TaskState.EDIT
        model.setTaskState(taskPosition, editableTaskState)

        val actualEditablePosition = model.getEditableTaskPosition()

        Assert.assertNotEquals(initialEditablePosition, actualEditablePosition)
        Assert.assertEquals(taskPosition, actualEditablePosition)
    }

    @Test
    fun `testSetTaskState from EDIT to SOME test that editable position changed`() {
        val expectedEditablePosition = -1
        val taskPosition = 0
        val initialTaskState = TaskState.EDIT
        model.setTaskState(taskPosition, initialTaskState)
        val initialEditablePosition = model.getEditableTaskPosition()

        Assert.assertEquals(taskPosition, initialEditablePosition)

        val expectedTaskState = TaskState.DEFAULT
        model.setTaskState(taskPosition, expectedTaskState)

        val actualEditablePosition = model.getEditableTaskPosition()

        Assert.assertNotEquals(initialEditablePosition, actualEditablePosition)
        Assert.assertEquals(actualEditablePosition, expectedEditablePosition)
    }

    @Test
    fun `setTaskMessage with valid params`() {
        val myList = model.getMyList()
        val initialTask = myList.first()
        val initialMessage = initialTask.message
        val expectedMessage = "Message updated"
        val taskPosition = 0

        model.setTaskMessage(taskPosition, expectedMessage)
        val actualMessage = model.getMyList().first().message

        Assert.assertNotEquals(initialMessage, actualMessage)
        Assert.assertEquals(actualMessage, expectedMessage)
    }

    @Test
    fun `getTaskFromPosition with valid param`() {
        val myList = model.getMyList()
        val testingTask = myList.first()
        val testingPosition = 0

        val actualTask = model.getTaskFromPosition(testingPosition)
        Assert.assertEquals(actualTask, testingTask)
    }

    @Test
    fun `getTaskFromPosition with invalid param`() {
        val testingTask = null
        val testingPosition = -1

        val actualTask = model.getTaskFromPosition(testingPosition)
        Assert.assertEquals(actualTask, testingTask)
    }
}