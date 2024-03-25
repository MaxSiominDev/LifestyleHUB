package dev.maxsiomin.prodhse.navdestinations

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

/** Don't forget to add new screens to [TopLevelDestination] */
sealed class Screen(val route: String, val arguments: List<NamedNavArgument> = emptyList()) {

    // TLD
    data object VenuesScreen : Screen("venues_screen")

    data object DetailsScreen : Screen(
        route = "details_screen/{fsqId}",
        arguments = listOf(
            navArgument("fsqId") { type = NavType.StringType }
        )
    )

    data object PhotoScreen : Screen(
        route = "photo_screen/{url}",
        arguments = listOf(
            navArgument("url") { type = NavType.StringType }
        )
    )

    data object AddPlanScreen : Screen(
        route = "add_plan_screen/{fsqId}",
        arguments = listOf(
            navArgument("fsqId") { type = NavType.StringType }
        )
    )

    // TLD
    data object PlannerScreen : Screen("planner_screen")

    data object EditPlanScreen : Screen(
        route = "edit_plan_screen/{planId}",
        arguments = listOf(
            navArgument("planId") { type = NavType.StringType }
        )
    )

    // TLD
    data object ProfileScreen : Screen("profile_screen")

    data object AuthScreen : Screen("auth_screen")
    data object LoginScreen : Screen("login_screen")
    data object SignupScreen : Screen("signup_screen")
    data object SuccessfulRegistrationScreen : Screen("successful_registration_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route.split("/").first())
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

}

