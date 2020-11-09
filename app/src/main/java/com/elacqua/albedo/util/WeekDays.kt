package com.elacqua.albedo.util

object WeekDays {
    fun getDayByIndex(index: Int): String {
        return when (index) {
            0 -> {
                "Monday"
            }
            1 -> {
                "Tuesday"
            }
            2 -> {
                "Wednesday"
            }
            3 -> {
                "Thursday"
            }
            4 -> {
                "Friday"
            }
            5 -> {
                "Saturday"
            }
            6 -> {
                "Sunday"
            }
            else -> {
                "Unknown"
            }
        }
    }
}