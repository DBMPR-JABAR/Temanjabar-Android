package id.go.jabarprov.dbmpr.temanjabar.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.BasemapStyle
import com.esri.arcgisruntime.mapping.Viewpoint
import id.go.jabarprov.dbmpr.temanjabar.feature.map.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMapBinding.inflate(layoutInflater) }

    private val mapView by lazy { binding.mvArcgis }

    private val locationDisplay by lazy { mapView.locationDisplay }

    private val requestLocationPermissionLauncher by lazy {
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            when {
                (permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false)
                        || (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false) -> {

                }
                else -> {

                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpUI()
        checkAndRequestLocationPermission()
    }

    private fun setUpUI() {
        setUpArcGISMap()
    }

    private fun setUpArcGISMap() {
        val map = ArcGISMap(BasemapStyle.ARCGIS_NAVIGATION)
        binding.mvArcgis.map = map
        binding.mvArcgis.setViewpoint(Viewpoint(-6.921359549350742, 107.61111502699526, 72000.0))
    }

    private fun checkAndRequestLocationPermission() {
        if (checkLocationPermission()) {

        } else {
            requestLocationPermission()
        }
    }

    private fun checkLocationPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestLocationPermission() {

    }
}