package dev.maxsiomin.prodhse.navdestinations

/** Don't forget to add new screens to [TopLevelDestination] */
sealed class Screen(val route: String) {

    // TLD
    data object VenuesScreen : Screen("venues_screen")

    // TLD
    data object PlannerScreen : Screen("planner_screen")

    // TLD
    data object AuthScreen : Screen("auth_screen")
    data object LoginScreen : Screen("login_screen")
    data object SignupScreen : Screen("signup_screen")
    data object SuccessfulRegistrationScreen : Screen("successful_registration_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append(arg)
            }
        }
    }

}

