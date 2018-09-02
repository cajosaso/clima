package com.cajosaso.clima

import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.rule.ActivityTestRule

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class WeatherActivityTest {

    @get:Rule
    var mActivityTestRule = ActivityTestRule(WeatherActivity::class.java)

    var monitor = getInstrumentation().addMonitor(CitiesActivity::class.java.getName(), null, false)

    @Test
    fun testLaunchWeatherActivity() {
        val fab = mActivityTestRule.activity.findViewById<android.support.design.widget.FloatingActionButton>(R.id.fab)
        assertNotNull(fab)
    }

}