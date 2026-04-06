package com.example.simbirsofttest.data

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

data class Task(
    val id: Int,
    val dateStart: Long,
    val dateFinish: Long,
    val name: String,
    val description: String
) {
    fun getLocalDate(): LocalDate =
        Instant.ofEpochSecond(dateStart).atZone(ZoneId.systemDefault()).toLocalDate()

    fun overlapsWithHour(date: LocalDate, hour: Int): Boolean {
        val hourStart = date.atStartOfDay(ZoneId.systemDefault())
            .plusHours(hour.toLong())
            .toEpochSecond()
        val hourEnd = hourStart + 3600

        return dateStart < hourEnd && dateFinish > hourStart
    }

    fun getTimeRange(): String {
        val start = Instant.ofEpochSecond(dateStart).atZone(ZoneId.systemDefault())
        val end = Instant.ofEpochSecond(dateFinish).atZone(ZoneId.systemDefault())
        return "${start.hour.toString().padStart(2, '0')}:${
            start.minute.toString().padStart(2, '0')
        } – " +
                "${end.hour.toString().padStart(2, '0')}:${end.minute.toString().padStart(2, '0')}"
    }
}