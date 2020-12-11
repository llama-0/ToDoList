package ru.faizovr.todo.presentation.mapper

import ru.faizovr.todo.R
import ru.faizovr.todo.domain.model.Task
import ru.faizovr.todo.domain.model.TaskState
import ru.faizovr.todo.presentation.viewholder.TaskDataView

class TaskMapper : EntityMapper<Task, TaskDataView> {
    override fun mapFromEntity(entity: Task): TaskDataView {
        return TaskDataView(
            isCheckBoxActive = when (entity.taskState) {
                TaskState.DEFAULT -> false
                TaskState.COMPLETE -> true
                TaskState.EDIT -> false
            },
            id = entity.id,
            message = entity.message,
            editButtonImageId = when (entity.taskState) {
                TaskState.DEFAULT -> R.drawable.ic_round_inactive_32
                TaskState.COMPLETE -> R.drawable.ic_round_inactive_32
                TaskState.EDIT -> R.drawable.ic_round_active_32
            }
        )
    }
}