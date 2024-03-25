package dev.maxsiomin.prodhse.core.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class DefaultLocationTracker @Inject constructor(
    private val context: Context,
    private val locationClient: FusedLocationProviderClient,
    private val permissionChecker: PermissionChecker,
) : LocationTracker {

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Location? {

        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if(!hasAccessCoarseLocationPermission || !isGpsEnabled) {
            return null
        }

        return suspendCancellableCoroutine { cont ->
            locationClient.lastLocation.apply {
                if(isComplete) {
                    if(isSuccessful) {
                        cont.resume(result)
                    } else {
                        cont.resume(null)
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    cont.resume(it)
                }
                addOnFailureListener {
                    cont.resume(null)
                }
                addOnCanceledListener {
                    cont.cancel()
                }
            }
        }
    }

    /*@SuppressLint("MissingPermission")
    override suspend fun getLocation(): Location = withContext(Dispatchers.IO) {
        suspendCancellableCoroutine { continuation ->
            if (permissionChecker.hasPermission(PermissionChecker.COARSE_LOCATION_PERMISSION).not()) {
                throw LocationTracker.LocationException("Missing location permission")
            }

            val locationManager = context.getSystemService(LocationManager::class.java)
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled =
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!isGpsEnabled && !isNetworkEnabled) {
                throw LocationTracker.LocationException("GPS is disabled")
            }

            val locationRequest = LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                numUpdates = 1 // Request a single update
            }

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationClient.removeLocationUpdates(this)
                    val result = locationResult.lastLocation
                    if (result == null) {
                        throw LocationTracker.LocationException("Something went wrong")
                    }
                    continuation.resume(result)
                }

                override fun onLocationAvailability(p0: LocationAvailability) {
                    ""
                }
            }

            locationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )

            continuation.invokeOnCancellation {
                locationClient.removeLocationUpdates(locationCallback)
            }
        }
    }*/
}