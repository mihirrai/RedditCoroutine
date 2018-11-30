package com.example.mihir.redditcoroutine

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented recycler_item_post_link, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under recycler_item_post_link.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.example.mihir.redditcoroutine", appContext.packageName)
    }
}
