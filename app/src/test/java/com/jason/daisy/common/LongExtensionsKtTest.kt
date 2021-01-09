package com.jason.daisy.common

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class LongExtensionsKtTest {

    @Test
    fun timeStringZero() {
        Assertions.assertEquals("0.00", (0L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun timeStringOneSecond() {
        Assertions.assertEquals("1.00", (1000L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun timeStringOneMinute() {
        Assertions.assertEquals("1:00.00", (60000L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun timeStringFiveMinutesArbitrary() {
        Assertions.assertEquals("5:26.79", (326794L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun timeStringOneMinuteOneSecond() {
        Assertions.assertEquals("1:01.23", (61230L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun timeString5MinutesHundredMilli() {
        Assertions.assertEquals("5:00.10", (300100L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun timeStringTenMillis() {
        Assertions.assertEquals("0.01", (10L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun timeStringTwentyMillis() {
        Assertions.assertEquals("0.02", (20L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun timeUntilMinute() {
        TODO("Rewrite tests")
        for (i in 0 .. 60000) {
            var tempString = i.toString()
            tempString = tempString.padStart(4, '0')
            tempString = "${tempString.substring(0 until tempString.length - 3)}.${tempString.substring(tempString.length - 3 until tempString.length - 1)}"
            val ans = (i.toLong().msToTimeString())
            if (i == 60000)
                Assertions.assertEquals(
                    "1:00.00",
                    (i.toLong().msToTimeString()),
                    "Unexpected Result"
                )
            else
                Assertions.assertEquals(
                    tempString,
                    (i.toLong().msToTimeString()),
                    "Unexpected Result"
                )
        }
    }

    @Test
    fun timeStringHourArbitrary() {
        Assertions.assertEquals("1:46:13.08", (6373089L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun timeStringHourOneDigitMinute() {
        Assertions.assertEquals("1:07:08.09", (4028090L).msToTimeString(), "Unexpected Result")
    }
}