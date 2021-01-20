package com.jason.daisy.common

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class LongExtensionsKtTest {

    @Test
    fun zero() {
        Assertions.assertEquals("0.00", (0L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun tenMs() {
        Assertions.assertEquals("0.01", (10L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun twentyMs() {
        Assertions.assertEquals("0.02", (20L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun hundredMs() {
        Assertions.assertEquals("0.10", (100L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun hundredTenMs() {
        Assertions.assertEquals("0.11", (110L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun oneS() {
        Assertions.assertEquals("1.00", (1_000L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun oneSTenMs() {
        Assertions.assertEquals("1.01", (1_010L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun tenS() {
        Assertions.assertEquals("10.00", (10_000L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun tenSTenMs() {
        Assertions.assertEquals("10.01", (10_010L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun elevenSTenMs() {
        Assertions.assertEquals("11.01", (11_010L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun oneM() {
        Assertions.assertEquals("1:00.00", (60_000L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun oneMTenMs() {
        Assertions.assertEquals("1:00.01", (60_010L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun oneMOneS() {
        Assertions.assertEquals("1:01.00", (61_000L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun oneMOneSTenMs() {
        Assertions.assertEquals("1:01.01", (61_010L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun oneMTenSTenMs() {
        Assertions.assertEquals("1:10.01", (70_010L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun arbitraryMSMs() {
        Assertions.assertEquals("5:26.79", (326_794L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun oneH() {
        Assertions.assertEquals("1:00:00.00", (3_600_000L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun oneHTenMs() {
        Assertions.assertEquals("1:00:00.01", (3_600_010L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun oneHOneS() {
        Assertions.assertEquals("1:00:01.00", (3_601_000L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun oneHTenS() {
        Assertions.assertEquals("1:00:10.00", (3_610_000L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun oneHOneM() {
        Assertions.assertEquals("1:01:00.00", (3_660_000L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun oneHTenM() {
        Assertions.assertEquals("1:10:00.00", (4_200_000L).msToTimeString(), "Unexpected Result")
    }

    @Test
    fun oneHTenMOneSTenMs() {
        Assertions.assertEquals("1:10:01.01", (4_201_010L).msToTimeString(), "Unexpected Result")
    }
}