package com.jayvijaygohil.data

import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.util.*

class TimeZoneRule(private val timeZone: String) : TestWatcher() {
    private val original: TimeZone = TimeZone.getDefault()

    override fun starting(description: Description) {
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone))
    }

    override fun finished(description: Description) {
        TimeZone.setDefault(original)
    }
}