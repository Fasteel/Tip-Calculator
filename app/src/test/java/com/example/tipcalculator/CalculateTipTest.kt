@file:Suppress("IllegalIdentifier")

package com.example.tipcalculator

import org.junit.Assert.assertEquals
import org.junit.Test

class CalculateTipTest {

    @Test
    fun `GIVEN no percentage WHEN calculating the amount THEN it should be 15 percent of the original amount`() {
        val result = calculateTip(100.0, false)

        assertEquals("€15.00", result)
    }

    @Test
    fun `GIVEN a 20 percentage WHEN calculating the amount THEN it should be 20 percent of the original amount`() {
        val result = calculateTip(100.0, false, 20.0)

        assertEquals("€20.00", result)
    }

    @Test
    fun `GIVEN a float amount WHEN calculating the amount THEN it should be 20,1 percent of the original amount`() {
        val result = calculateTip(100.0, false, 20.1)

        assertEquals("€20.10", result)
    }

    @Test
    fun `GIVEN a float amount WHEN calculating the rounded amount THEN it should be 21 percent of the original amount`() {
        val result = calculateTip(100.0, true, 20.1)

        assertEquals("€21.00", result)
    }
}
