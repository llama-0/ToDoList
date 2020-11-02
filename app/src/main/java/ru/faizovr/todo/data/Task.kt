package ru.faizovr.todo.data

class Task(val message: String)
{
    val id: Long

    init {
        id = fillId
        fillId++
    }

    companion object {
        var fillId: Long = 0
    }
}