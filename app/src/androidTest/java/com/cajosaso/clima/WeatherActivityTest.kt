package com.cajosaso.clima

import android.app.Activity
import android.app.Instrumentation
import android.support.test.InstrumentationRegistry
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.widget.TextView
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class WeatherActivityTest {

    @get:Rule
    var mActivityTestRule = ActivityTestRule(WeatherActivity::class.java)

    var monitor = getInstrumentation().addMonitor(CountriesActivity::class.java.getName(), null, false)

    @Test
    fun testLaunchWeatherActivity() {
        val fab = mActivityTestRule.activity.findViewById<android.support.design.widget.FloatingActionButton>(R.id.fab)
        assertNotNull(fab)
    }

}