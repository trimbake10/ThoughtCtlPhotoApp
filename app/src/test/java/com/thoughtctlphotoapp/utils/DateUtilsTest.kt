package com.thoughtctlphotoapp.utils

import org.junit.Assert
import org.junit.Test

class DateUtilsTest {

    @Test
    fun getPostDate() {
        val dateUtilsTest = DateUtils.getPostDate(1704450215727L)
        Assert.assertEquals("05/01/2024 03:53 pm", dateUtilsTest)
    }

    @Test
    fun getWrongPostDate(){
        val dateUtilsTest = DateUtils.getPostDate(0)
        Assert.assertNotEquals("05/01/2024 03:53 pm", dateUtilsTest)
    }
}