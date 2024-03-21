package dev.maxsiomin.prodhse.navdestinations

sealed class Screen(val route: String) {

    data object VenuesScreen : Screen("venues_screen")
    data object PlannerScreen : Screen("planner_screen")
    data object AuthScreen : Screen("auth_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append(arg)
            }
        }
    }

}

