package dev.maxsiomin.prodhse.core.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import dev.maxsiomin.common.domain.resource.LocationError
import dev.maxsiomin.common.domain.resource.Resource
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

@SuppressLint("MissingPermission")
class DefaultLocationTracker @Inject constructor(
    private val context: Context,
    private val locationClient: FusedLocationProviderClient,
) : LocationTracker {

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

        val lastLocation = getLastLocation()
        if (lastLocation != null) {
            return Resource.Success(lastLocation)
        }

        val newLocation = loadNewLocation()
        if (newLocation != null) {
            return Resource.Success(newLocation)
        }

        return Resource.Error(LocationError.Unknown)
    }

    private suspend fun getLastLocation(): Location? {
        val deferred = CompletableDeferred<Location?>()
        locationClient.lastLocation.addOnCompleteListener {
            deferred.complete(it.result)
        }
        return deferred.await()
    }

    private suspend fun loadNewLocation(): Location? {
        val deferred = CompletableDeferred<Location?>()
        locationClient.getCurrentLocation(
            CurrentLocationRequest.Builder().setPriority(PRIORITY_HIGH_ACCURACY).build(),
            null,
        ).addOnCompleteListener {
            deferred.complete(it.result)
        }
        return deferred.await()
    }


}
