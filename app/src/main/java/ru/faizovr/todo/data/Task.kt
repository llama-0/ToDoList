package ru.faizovr.todo.data

class Task(val id: Long, var message: String) {

    override fun equals(other: Any?): Boolean {
        if (other is Task) {
            val compare = other as Task
            if (compare.message == message) {
               return true
            }
        } else {
            return false
        }
        return false
    }

    fun copy(): Task =
        Task(id, message)
}