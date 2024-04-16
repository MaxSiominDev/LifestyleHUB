package dev.maxsiomin.prodhse.core.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.google.android.gms.location.FusedLocationProviderClient
import dev.maxsiomin.common.domain.resource.LocationError
import dev.maxsiomin.common.domain.resource.Resource
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

class DefaultLocationTracker @Inject constructor(
    private val context: Context,
    private val locationClient: FusedLocationProviderClient,
) : LocationTracker {

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Resource<Location, LocationError> {
        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasAccessCoarseLocationPermission.not()) {
            return Resource.Error(LocationError.MissingPermission)
        }

        val locationManager = context.getSystemService<LocationManager>()!!
        val isGpsEnabled =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                    locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (isGpsEnabled.not()) {
            return Resource.Error(LocationError.GpsDisabled)
        }

        val deferred = CompletableDeferred<Resource<Location, LocationError>>()

        locationClient.lastLocation.apply {
            addOnSuccessListener {
                deferred.complete(Resource.Success(result))
            }
            addOnFailureListener {
                deferred.complete(Resource.Error(LocationError.Unknown))
            }
        }

        return deferred.await()
    }

}
