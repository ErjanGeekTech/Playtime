package com.erjan.playtime.time

fun Long.parseToTimeString(): String {
    val seconds = this / 1000
    val secondsFormatted = (seconds % 60).pad(2)
    val minutes = seconds / 60
    val minutesFormatted = (minutes % 60).pad(2)
    val hours = minutes / 60
    val hoursFormatted = hours.pad(2)

    return "$hoursFormatted:$minutesFormatted:$secondsFormatted"
}

private fun Long.pad(desiredLength: Int) = this.toString().padStart(desiredLength, '0')