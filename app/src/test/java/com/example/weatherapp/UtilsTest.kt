package com.example.weatherapp

import org.junit.Assert
import org.junit.Test

class UtilsTest {

    @Test
    fun testCheckValidInput_WhenBothLatitudeLongitudeAreValid_ShouldReturnTrue() {
        val latitude = "13.34"
        val longitude = "167.78"
        val expectedResult = Utils.checkValidInput(latitude,longitude)
        Assert.assertTrue(expectedResult)
    }

    @Test
    fun testCheckValidInput_WhenBothLatitudeLongitudeAreInvalid_ShouldReturnFalse() {
        val latitude = ""
        val longitude = "167.78"
        val expectedResult = Utils.checkValidInput(latitude,longitude)
        Assert.assertFalse(expectedResult)
    }
}