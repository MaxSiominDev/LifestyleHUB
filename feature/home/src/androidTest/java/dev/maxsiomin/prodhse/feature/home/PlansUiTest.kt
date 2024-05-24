package dev.maxsiomin.prodhse.feature.home

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dev.maxsiomin.prodhse.feature.home.di.DatabaseModule
import dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.planner.PlannerScreenRoot
import dev.maxsiomin.prodhse.testing.HiltTestActivity
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@LargeTest
@UninstallModules(DatabaseModule::class)
internal class PlansUiTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<HiltTestActivity>()

    @Test
    fun launchHomeScreen(): Unit = runBlocking {
        composeRule.setContent {
            PlannerScreenRoot(navController = rememberNavController(), showSnackbar = {})
        }
        composeRule.onNodeWithText("No plans saved").assertExists()
    }

}