package com.example.quizwiz3

import android.content.Intent
import android.widget.ImageButton
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith




@RunWith(AndroidJUnit4::class)
class Dashboard2Test {



    @get:Rule
    var activityRule: ActivityTestRule<Dashboard2> = ActivityTestRule(Dashboard2::class.java)

    @Test
    fun testAnimalButtonLaunchesMultiChoice() {
        // Initialize intents
        Intents.init()

        // Simulate clicking the "animalbtn" button
        onView(withId(R.id.animalbtn)).perform(click())

        // Verify if the next activity is SingleMultigame
        Intents.intended(hasComponent(SingleMultigame::class.java.name))

        // Release intents
        Intents.release()
    }

    @Test
    fun testFoodButtonLaunchesMultiChoice() {
        Intents.init()

        onView(withId(R.id.btnfood)).perform(click())
        Intents.intended(hasComponent(SingleMultigame::class.java.name))

        Intents.release()
    }

    @Test
    fun testDisneyButtonLaunchesTrueOrFalse() {
        Intents.init()

        onView(withId(R.id.btndisney2)).perform(click())
        Intents.intended(hasComponent(TrueorFalse2::class.java.name))

        Intents.release()
    }


    @Test
    fun testMusicButtonLaunchesTrueOrFalse() {
        Intents.init()

        onView(withId(R.id.btnmusic)).perform(click())
        Intents.intended(hasComponent(SingleMultigame::class.java.name))

        Intents.release()
    }


    @Test
    fun testHistoryButtonLaunchesTrueOrFalse() {
        Intents.init()

        onView(withId(R.id.btnhistory)).perform(click())
        Intents.intended(hasComponent(TrueorFalse2::class.java.name))

        Intents.release()
    }

    @Test
    fun testTvshowsButtonLaunchesTrueOrFalse() {
        Intents.init()

        onView(withId(R.id.btntvshows)).perform(click())
        Intents.intended(hasComponent(TrueorFalse2::class.java.name))

        Intents.release()
    }



}
