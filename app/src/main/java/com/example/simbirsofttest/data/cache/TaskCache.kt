package com.example.simbirsofttest.data.cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.simbirsofttest.data.Task

@Entity(tableName = "task_table")
data class TaskCache(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dateStart: Long,
    val dateFinish: Long,
    val name: String,
    val description: String
)

fun TaskCache.toTask() = Task(
    id = id,
    dateStart = dateStart,
    dateFinish = dateFinish,
    name = name,
    description = description
)

fun Task.toCache() = TaskCache(
    id = id,
    dateStart = dateStart,
    dateFinish = dateFinish,
    name = name,
    description = description
)