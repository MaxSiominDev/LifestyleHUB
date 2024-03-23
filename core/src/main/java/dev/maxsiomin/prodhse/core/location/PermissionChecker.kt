package dev.maxsiomin.prodhse.core.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import javax.inject.Inject

interface PermissionChecker {

    fun hasPermission(permission: String): Boolean

    companion object {
        const val COARSE_LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION
        const val FINE_LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
    }

}

class PermissionCheckerImpl @Inject constructor(private val context: Context) : PermissionChecker {

    override fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission,
        ) == PackageManager.PERMISSION_GRANTED
    }
}