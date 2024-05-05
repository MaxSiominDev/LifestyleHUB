package dev.maxsiomin.prodhse.feature.home.domain.use_case.other

import dev.maxsiomin.prodhse.core.location.PermissionChecker
import javax.inject.Inject

internal class CheckLocationPermissionUseCase @Inject constructor(private val permissionChecker: PermissionChecker) {

    /** It's important that I consider location permission granted even though only COARSE is granted */
    operator fun invoke(): Boolean {
        return permissionChecker.hasPermission(PermissionChecker.COARSE_LOCATION_PERMISSION)
    }

}