package com.jason.daisy

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MainViewModelKtTest {

    @Test
    fun timeStringZero() {
        assertEquals("0.00", millisToTimeString(0L), "Unexpected Result")
    }

    @Test
    fun timeStringOneSecond() {
        assertEquals("1.00", millisToTimeString(1000L), "Unexpected Result")
    }

    @Test
    fun timeStringOneMinute() {
        assertEquals("1:00.00", millisToTimeString(60000L), "Unexpected Result")
    }

    @Test
    fun timeStringFiveMinutesArbitrary() {
        assertEquals("5:26.79", millisToTimeString(326794L), "Unexpected Result")
    }

    @Test
    fun timeStringOneMinuteOneSecond() {
        assertEquals("1:01.23", millisToTimeString(61230), "Unexpected Result")
    }

    @Test
    fun timeString5MinutesHundredMilli() {
        assertEquals("5:00.10", millisToTimeString(300100L), "Unexpected Result")
    }

    @Test
    fun timeStringTenMillis() {
        assertEquals("0.01", millisToTimeString(10L), "Unexpected Result")
    }

    @Test
    fun timeStringTwentyMillis() {
        assertEquals("0.02", millisToTimeString(20L), "Unexpected Result")
    }

    @Test
    fun timeUntilMinute() {
        for (i in 0 .. 60000) {
            var tempString = i.toString()
            tempString = tempString.padStart(4, '0')
            tempString = "${tempString.substring(0 until tempString.length - 3)}.${tempString.substring(tempString.length - 3 until tempString.length - 1)}"
            val ans = millisToTimeString(i.toLong())
            if (i == 60000)
                assertEquals("1:00.00", millisToTimeString(i.toLong()), "Unexpected Result")
            else
                assertEquals(tempString, millisToTimeString(i.toLong()), "Unexpected Result")
        }
    }

    @Test
    fun timeStringHourArbitrary() {
        assertEquals("1:46:13.08", millisToTimeString(6373089L), "Unexpected Result")
    }
}
