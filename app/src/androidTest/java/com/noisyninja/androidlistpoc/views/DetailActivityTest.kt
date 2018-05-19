package com.noisyninja.androidlistpoc.views

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.noisyninja.androidlistpoc.BaseTest
import com.noisyninja.androidlistpoc.R.id
import com.noisyninja.androidlistpoc.R.string
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by sudiptadutta on 19/05/18.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest : BaseTest() {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(DetailActivity::class.java)

    @Test
    fun testFirstEntry() {
        onView(withId(id.detail)).check(matches(withText(string.out_of_scope)))
    }
}