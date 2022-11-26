package com.dbtechprojects.pokemonApp.ui


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.dbtechprojects.pokemonApp.R
import com.dbtechprojects.pokemonApp.ui.activities.MainActivity
import com.dbtechprojects.pokemonApp.ui.adapters.PokemonListAdapter
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

    @Test
    fun confirmNavigationToDetailFragment() {
        onView(withId(R.id.listFragment_rv))
            .perform(RecyclerViewActions.actionOnItemAtPosition<PokemonListAdapter.PokemonViewHolder>(
                0, click()
            ))

        val textView = onView(withId(R.id.detailFragment_title_name))
        val mapView = onView(withId(R.id.detailFragment_map_ll))
        textView.check(matches(isDisplayed()))
        mapView.check(matches(isDisplayed()))
    }

    @Test
    fun confirmFilterDialog() {
        onView(withId(R.id.listFragment_filterImg)).perform(click())

        val grassImg = onView(withId(R.id.dialog_grass_img))
        val fireImg = onView(withId(R.id.dialog_fire_img))
        val waterImg = onView(withId(R.id.dialog_water_img))
        grassImg.check(matches(isDisplayed()))
        fireImg.check(matches(isDisplayed()))
        waterImg.check(matches(isDisplayed()))
    }

    @Test
    fun savePokemonTest(){
        // click on rv item
        onView(withId(R.id.listFragment_rv))
            .perform(RecyclerViewActions.actionOnItemAtPosition<PokemonListAdapter.PokemonViewHolder>(
                0, click()
            ))
        // save item
        onView(withId(R.id.parent)).perform(swipeUp())
        onView(withId(R.id.detailFragment_save_button)).perform(scrollTo(),click()) //make sure scrollview has no padding

        // back
        pressBack()
        onView(withId(R.id.listFragment_saved_FAB)).perform(click())

        // recyclerview has 1 child
        onView(withId(R.id.savedFragment_rv)).check(matches(hasChildCount(1)))
    }
}
