package ru.faizovr.todo.presentation.viewholder

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TaskDataView(
    val isCheckBoxActive: Boolean,
    val id: Long,
    val message: String,
    val editButtonImageId: Int
) : Parcelable