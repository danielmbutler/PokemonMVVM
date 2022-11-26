package com.dbtechprojects.pokemonApp.ui


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.dbtechprojects.pokemonApp.R
import com.dbtechprojects.pokemonApp.ui.activities.MainActivity
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun confirmNavigationButtonsExist() {
        val imageView = onView(
            allOf(
                withId(R.id.listFragment_saved_FAB),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val imageView2 = onView(
            allOf(
                withId(R.id.listFragment_map_FAB),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        imageView2.check(matches(isDisplayed()))
    }

    @Test
    fun confirmNavigationToSavedFragment() {
        val imageView = onView(withId(R.id.listFragment_saved_FAB))
        imageView.perform(click())

        val textView = onView(withId(R.id.SavedFragment_Title))

        textView.check(matches(isDisplayed()))
    }

    @Test
    fun confirmNavigationToMapFragment() {
        val imageView = onView(withId(R.id.listFragment_map_FAB))
        imageView.perform(click())

        val textView = onView(withId(R.id.mapFragment_Title))

        textView.check(matches(isDisplayed()))
    }
}
