package com.noisyninja.androidlistpoc

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import com.codewaves.stickyheadergrid.StickyHeaderGridAdapter
import com.noisyninja.androidlistpoc.views.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by sudiptadutta on 18/05/18.
 */

@RunWith(AndroidJUnit4::class)
class MainActivityTest : BaseTest() {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testFirstEntry() {

    }

    @Test
    fun mainActivityTest() {

        sleepMedium()

        val recyclerView = onView(
                allOf(withId(R.id.recyclerList)))

        val floatingActionButton = onView(
                allOf(withId(R.id.fab)))


        recyclerView.perform(actionOnItemAtPosition<StickyHeaderGridAdapter.ViewHolder>(2, click()))
        sleepShort()
        pressBack()
        floatingActionButton.perform(click())
        sleepShort()

        recyclerView.perform(actionOnItemAtPosition<StickyHeaderGridAdapter.ViewHolder>(1, click()))
        sleepShort()
        pressBack()
        floatingActionButton.perform(click())
        sleepShort()

    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return (parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position))
            }
        }
    }
}
