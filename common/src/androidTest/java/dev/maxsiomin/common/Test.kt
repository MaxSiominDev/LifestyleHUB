package dev.maxsiomin.common

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.maxsiomin.prodhse.testing.HiltTestActivity
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@LargeTest
class WeatherAndPlansE2ETest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<HiltTestActivity>()

    @Test
    fun launchHomeScreen() {
        composeRule.setContent {
            //val appState = rememberProdhseAppState()
            //ProdhseApp(appState = appState)
        }
    }

}