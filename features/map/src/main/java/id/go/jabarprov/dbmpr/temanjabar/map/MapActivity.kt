package id.go.jabarprov.dbmpr.temanjabar.map

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.esri.arcgisruntime.data.ServiceFeatureTable
import com.esri.arcgisruntime.layers.FeatureLayer
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.BasemapStyle
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.LocationDisplay
import com.esri.arcgisruntime.symbology.SimpleLineSymbol
import com.esri.arcgisruntime.symbology.SimpleRenderer
import id.go.jabarprov.dbmpr.temanjabar.feature.map.databinding.ActivityMapBinding

private const val TAG = "MapActivity"

class MapActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMapBinding.inflate(layoutInflater) }

    private val mapView by lazy { binding.mvArcgis }

    private val locationDisplay by lazy { mapView.locationDisplay }

    private val requestLocationPermissionLauncher by lazy {
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            when {
                (permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false)
                        || (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false) -> {
                    locateLocation()
                }
                else -> {
                    Toast.makeText(this, "Izin Lokasi Ditolak", Toast.LENGTH_SHORT).show()
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

        // create the service feature table
        val serviceFeatureTable =
            ServiceFeatureTable("https://tj.temanjabar.net/geoserver/gsr/services/temanjabar/FeatureServer/8/")

//        serviceFeatureTable.requestConfiguration.headers["ak"] = "9bea4cef-904d-4e00-adb2-6e1cf67b24ae"

//        Log.d(TAG, "setUpArcGISMap: ${serviceFeatureTable.requestConfiguration}")
        // create the feature layer using the service feature table
        val featureLayer = FeatureLayer(serviceFeatureTable)

        featureLayer.addLoadStatusChangedListener {
            Log.d(TAG, "setUpArcGISMap: STATUS ${it.newLoadStatus.name}")
        }

        featureLayer.addDoneLoadingListener {
            Log.d(TAG, "setUpArcGISMap: ${featureLayer.name}")
            if (featureLayer.loadError != null) {
                Log.d(TAG, "setUpArcGISMap: ${featureLayer.loadError}")
            }
        }

        // Simple renderer for override style in feature layer
        val lineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.rgb(0, 0, 255), 2f)
        val simpleRenderer = SimpleRenderer(lineSymbol)

//        featureLayer.renderer = simpleRenderer

        map.operationalLayers.add(featureLayer)
    }

    private fun checkAndRequestLocationPermission() {
        if (checkLocationPermission()) {
            locateLocation()
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
        requestLocationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

    private fun locateLocation() {
        locationDisplay.autoPanMode = LocationDisplay.AutoPanMode.RECENTER
        locationDisplay.startAsync()
    }

    override fun onPause() {
        mapView.pause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.resume()
    }

    override fun onDestroy() {
        mapView.dispose()
        super.onDestroy()
    }
}