package com.jason.daisy.viewsolves

import com.jason.daisy.database.Solve
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class SolveDiffUtilItemCallbackTest {

    @Test
    fun areItemsTheSame() {
        val s1 = Solve("3:02.22", "2021-01-09T15:13:55.97")
        val s2 = Solve("3:02.22", "2021-01-09T15:13:55.97")
        Assertions.assertTrue(SolveDiffUtilItemCallback.areItemsTheSame(s1, s2))
    }

    @Test
    fun areContentsTheSame() {
        //Same as above test, we don't care about different objects
    }
}