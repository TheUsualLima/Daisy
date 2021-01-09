package com.jason.daisy.common

fun Long.msToTimeString() : String {
    val s : String
    val hours = this / 1000 / 60 / 60
    val minutes = this / 1000 / 60 % 60
    val seconds = this / 1000 % 60
    val milliseconds = this % 1000 / 10

    s = when {
        hours > 0 -> "$hours:" +
                "${minutes.toString().padStart(2, '0')}:" +
                "${seconds.toString().padStart(2, '0')}." +
                milliseconds.toString().padStart(2, '0')
        minutes > 0 -> "$minutes:" +
                "${seconds.toString().padStart(2, '0')}." +
                milliseconds.toString().padStart(2, '0')
        else -> "$seconds." +
                milliseconds.toString().padStart(2, '0')
    }

    return s
}
