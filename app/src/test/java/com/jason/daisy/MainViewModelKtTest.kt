package com.jason.daisy

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MainViewModelKtTest {

    @Test
    fun timeStringZero() {
        assertEquals("0.00", msToTimeString(0L), "Unexpected Result")
    }

    @Test
    fun timeStringOneSecond() {
        assertEquals("1.00", msToTimeString(1000L), "Unexpected Result")
    }

    @Test
    fun timeStringOneMinute() {
        assertEquals("1:00.00", msToTimeString(60000L), "Unexpected Result")
    }

    @Test
    fun timeStringFiveMinutesArbitrary() {
        assertEquals("5:26.79", msToTimeString(326794L), "Unexpected Result")
    }

    @Test
    fun timeStringOneMinuteOneSecond() {
        assertEquals("1:01.23", msToTimeString(61230), "Unexpected Result")
    }

    @Test
    fun timeString5MinutesHundredMilli() {
        assertEquals("5:00.10", msToTimeString(300100L), "Unexpected Result")
    }

    @Test
    fun timeStringTenMillis() {
        assertEquals("0.01", msToTimeString(10L), "Unexpected Result")
    }

    @Test
    fun timeStringTwentyMillis() {
        assertEquals("0.02", msToTimeString(20L), "Unexpected Result")
    }

    @Test
    fun timeUntilMinute() {
        for (i in 0 .. 60000) {
            var tempString = i.toString()
            tempString = tempString.padStart(4, '0')
            tempString = "${tempString.substring(0 until tempString.length - 3)}.${tempString.substring(tempString.length - 3 until tempString.length - 1)}"
            val ans = msToTimeString(i.toLong())
            if (i == 60000)
                assertEquals("1:00.00", msToTimeString(i.toLong()), "Unexpected Result")
            else
                assertEquals(tempString, msToTimeString(i.toLong()), "Unexpected Result")
        }
    }

    @Test
    fun timeStringHourArbitrary() {
        assertEquals("1:46:13.08", msToTimeString(6373089L), "Unexpected Result")
    }

    @Test
    fun timeStringHourOneDigitMinute() {
        assertEquals("1:07:08.09", msToTimeString(4028090L), "Unexpected Result")
    }
}
