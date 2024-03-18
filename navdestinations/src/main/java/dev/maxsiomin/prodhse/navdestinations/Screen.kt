package dev.maxsiomin.prodhse.navdestinations

sealed class Screen(val route: String) {

    data object TabsScreen : Screen("tabs_screen")
    // All following screens belong to tabs screen
    data object VenuesGraph : Screen("venues_graph")
    data object VenuesScreen : Screen("venues_screen")

    data object PlannerGraph : Screen("planner_graph")
    data object PlannerScreen : Screen("planner_screen")

    data object AuthGraph : Screen("auth_graph")
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
