package ru.faizovr.todo.data

import org.junit.Assert
import org.junit.Before
import org.junit.Test


class ModelTest {

    private val model = Model()

    @Before
    fun before() {
        model.addTask("test 1")
        model.addTask("test 2")
        model.addTask("test 3")
    }

    @Test
    fun testAddTask() {
        model.addTask("TEST")
        Assert.assertEquals(model.getMyList()[model.getMyList().size - 1].message, "TEST")
    }

    @Test
    fun testDeleteTask() {
        model.deleteTask(model.getMyList().size)
        Assert.assertEquals(model.getMyList()[model.getMyList().size - 1].message, "test 3")
    }

    @Test
    fun testInvalidDeleteTask() {
        val taskList: List<Task> = model.getMyList()
        model.deleteTask(-1)
        model.deleteTask(1000)
        model.deleteTask(100000000)
        Assert.assertEquals(model.getMyList(), taskList)
    }

    @Test
    fun testSwapTask() {
        val first: Task? = model.getTaskFromPosition(0)
        val last: Task? = model.getTaskFromPosition(1)
        model.swapTask(0, 1)
        Assert.assertEquals(model.getMyList()[0], last)
        Assert.assertEquals(model.getMyList()[1], first)
    }

    @Test
    fun testInvalidSwapTask() {
        val taskList: List<Task> = model.getMyList()
        model.swapTask(-1, -2)
        model.swapTask(-1, 1)
        model.swapTask(1, 1000)
        model.swapTask(1, 1)
        Assert.assertEquals(taskList, model.getMyList())
    }

    @Test
    fun testSetTaskState() {
        model.setTaskState(0, TaskState.EDIT)
        Assert.assertEquals(model.getMyList()[0].taskState, TaskState.EDIT)

        model.setTaskState(0, TaskState.COMPLETE)
        Assert.assertEquals(model.getMyList()[0].taskState, TaskState.COMPLETE)

        model.setTaskState(0, TaskState.DEFAULT)
        Assert.assertEquals(model.getMyList()[0].taskState, TaskState.DEFAULT)
    }

    @Test
    fun testInvalidSetTaskState() {
        val taskList: List<Task> = model.getMyList()
        model.setTaskState(-1, TaskState.EDIT)
        model.setTaskState(-100, TaskState.DEFAULT)
        model.setTaskState(1000000, TaskState.COMPLETE)
        Assert.assertEquals(model.getMyList(), taskList)
    }

    @Test
    fun testGetEditableTaskPosition() {
        model.setTaskState(0, TaskState.EDIT)
        Assert.assertEquals(model.getEditableTaskPosition(), 0)
        model.setTaskState(0, TaskState.COMPLETE)
        Assert.assertEquals(model.getEditableTaskPosition(), -1)
    }

    @Test
    fun testGetEditableTaskMessage() {
        model.setTaskState(0, TaskState.EDIT)
        Assert.assertEquals(model.getEditableTaskMessage(), model.getTaskFromPosition(0)?.message)
        model.setTaskState(0, TaskState.COMPLETE)
        Assert.assertEquals(model.getEditableTaskMessage(), "")
    }

    @Test
    fun testGetTaskFromPosition() {
        Assert.assertEquals(model.getTaskFromPosition(-1), null)
        Assert.assertEquals(model.getTaskFromPosition(0), model.getMyList()[0])
    }

}