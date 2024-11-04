package com.fake.quizwiz3

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DashboardTest {

    @Before
    fun setUp() {
        // Initialize intents to capture them
        Intents.init()
        // Launch the activity
        ActivityScenario.launch(Dashboard::class.java)
    }

    @After
    fun tearDown() {
        // Release intents after tests
        Intents.release()
    }

    @Test
    fun testAnimalButtonLaunchesMultiChoiceActivity() {
        onView(withId(R.id.animalbtn)).perform(click())
        Intents.intended(hasComponent(MultiChoice::class.java.name))
    }

    @Test
    fun testFoodButtonLaunchesMultiChoiceActivity() {
        onView(withId(R.id.btnfood)).perform(click())
        Intents.intended(hasComponent(MultiChoice::class.java.name))
    }

    @Test
    fun testMusicButtonLaunchesMultiChoiceActivity() {
        onView(withId(R.id.btnmusic)).perform(click())
        Intents.intended(hasComponent(MultiChoice::class.java.name))
    }

    @Test
    fun testDisneyButtonLaunchesTrueOrFalseActivity() {
        onView(withId(R.id.btndisney)).perform(click())
        Intents.intended(hasComponent(TrueorFalse::class.java.name))
    }

    @Test
    fun testTVShowsButtonLaunchesTrueOrFalseActivity() {
        onView(withId(R.id.btntvshows)).perform(click())
        Intents.intended(hasComponent(TrueorFalse::class.java.name))
    }

    @Test
    fun testHistoryButtonLaunchesTrueOrFalseActivity() {
        onView(withId(R.id.btnhistory)).perform(click())
        Intents.intended(hasComponent(TrueorFalse::class.java.name))
    }
}
