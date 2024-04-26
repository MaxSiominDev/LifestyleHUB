package dev.maxsiomin.prodhse.navdestinations

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

/** Don't forget to add new screens to [TopLevelDestination] */
sealed class Screen(val route: String, val arguments: List<NamedNavArgument> = emptyList()) {

    // All TLDs that have own backstack are defined here
    object Tld {
        data object Home : Screen("home_tld")
        data object Planner : Screen("planner_tld")
        data object Profile : Screen("profile_tld")
    }


    data object HomeScreen : Screen("home_screen")

    object DetailsScreenArgs {
        const val FSQ_ID = "fsqId"
    }

    data object DetailsScreen : Screen(
        route = "details_screen".arg(DetailsScreenArgs.FSQ_ID),
        arguments = listOf(
            navArgument(DetailsScreenArgs.FSQ_ID) { type = NavType.StringType }
        )
    )


    object BrowsePhotoScreenArgs {
        const val URL = "url"
    }

    data object BrowsePhotoScreen : Screen(
        route = "browse_photo_screen".arg(BrowsePhotoScreenArgs.URL),
        arguments = listOf(
            navArgument(BrowsePhotoScreenArgs.URL) { type = NavType.StringType }
        )
    )


    object AddPlanScreenArgs {
        const val FSQ_ID = "fsqId"
    }

    data object AddPlanScreen : Screen(
        route = "add_plan_screen".arg(AddPlanScreenArgs.FSQ_ID),
        arguments = listOf(
            navArgument(AddPlanScreenArgs.FSQ_ID) { type = NavType.StringType }
        )
    )

    data object PlannerScreen : Screen("planner_screen")

    object EditPlanScreenArgs {
        const val PLAN_ID = "planId"
    }

    data object EditPlanScreen : Screen(
        route = "edit_plan_screen".arg(EditPlanScreenArgs.PLAN_ID),
        arguments = listOf(
            navArgument(EditPlanScreenArgs.PLAN_ID) { type = NavType.StringType }
        )
    )

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

    private companion object {
        fun String.arg(name: String): String {
            return "$this/{$name}"
        }
    }

}
