package id.go.jabarprov.dbmpr.utils.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.tasks.CancellationToken
import id.go.jabarprov.dbmpr.utils.extensions.showToast
import kotlinx.coroutines.tasks.await

class LocationUtils(private val context: Activity) {

    private val locationManager by lazy { context.getSystemService(Context.LOCATION_SERVICE) as LocationManager }

    private val clientPlayService by lazy { LocationServices.getSettingsClient(context) }

    private val locationRequest by lazy {
        LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private val locationSettingsRequest by lazy {
        LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build()
    }

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(
            context
        )
    }

    fun isLocationEnabled(): Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    suspend fun enableLocationService() {
        try {
            clientPlayService.checkLocationSettings(locationSettingsRequest).await()
        } catch (e: ResolvableApiException) {
            e.startResolutionForResult(context, REQUEST_CODE)
        } catch (e: IntentSender.SendIntentException) {
            context.showToast("Lokasi GPS Tidak Diaktifkan")
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(cancellationToken: CancellationToken): Location? {
        return fusedLocationClient.getCurrentLocation(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            cancellationToken
        ).await()
    }

    @SuppressLint("MissingPermission")
    suspend fun getLastLocation(): Location? {
        return fusedLocationClient.lastLocation.await()
    }

    companion object {
        const val REQUEST_CODE = 938
    }

}