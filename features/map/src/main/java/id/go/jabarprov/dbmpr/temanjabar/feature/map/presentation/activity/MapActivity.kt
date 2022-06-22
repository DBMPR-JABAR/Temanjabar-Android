package id.go.jabarprov.dbmpr.temanjabar.feature.map.presentation.activity

import android.Manifest
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.BasemapStyle
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.LocationDisplay
import com.google.android.material.bottomsheet.BottomSheetBehavior
import id.go.jabarprov.dbmpr.temanjabar.feature.map.R
import id.go.jabarprov.dbmpr.temanjabar.feature.map.databinding.ActivityMapBinding
import id.go.jabarprov.dbmpr.temanjabar.feature.map.utils.ArcGISMapUtils
import id.go.jabarprov.dbmpr.temanjabar.navigation.map.MapArgs
import id.go.jabarprov.dbmpr.utils.extensions.checkPermission
import id.go.jabarprov.dbmpr.utils.utils.LocationUtils
import kotlinx.coroutines.launch

private const val TAG = "MapActivity"

class MapActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMapBinding.inflate(layoutInflater) }

    private val mapArcgis by lazy {
        ArcGISMap(BasemapStyle.ARCGIS_NAVIGATION).apply {
            minScale = 1000000.0
            maxScale = 3000.0
        }
    }

    private val mapView by lazy {
        binding.mvArcgis
    }

    private val locationDisplay by lazy { mapView.locationDisplay }

    private val locationUtils by lazy { LocationUtils(this) }

    private val lat by lazy { intent.getDoubleExtra(MapArgs.LAT, -1.0) }

    private val long by lazy { intent.getDoubleExtra(MapArgs.LONG, -1.0) }

    private val bottomSheetBehaviour by lazy {
        BottomSheetBehavior.from(binding.bottomSheet)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView)
        windowInsetsController?.let {
            it.isAppearanceLightStatusBars = true
        }

        setContentView(binding.root)

        initUI()
        initBottomSheet()
        setUpArcGISMap()
        if (checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION) && checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            initLocation()
        }
    }

    private fun initUI() {
        binding.apply {
            buttonBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun initBottomSheet() {
        binding.bottomSheet.apply {
            bottomSheetBehaviour.apply {
                isHideable = true
                state = BottomSheetBehavior.STATE_HIDDEN
            }
        }
    }

    private fun setEdgeToEdgeDisplay(value: Boolean) {
        if (value) {
            window?.statusBarColor =
                ContextCompat.getColor(this, android.R.color.transparent)
            WindowCompat.setDecorFitsSystemWindows(window!!, false)
            ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, windowInsets ->
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
                binding.buttonBack.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    topMargin = insets.top + 32
                }
                WindowInsetsCompat.CONSUMED
            }
        } else {
            window?.statusBarColor =
                ContextCompat.getColor(
                    this,
                    id.go.jabarprov.dbmpr.core_views.R.color.white
                )
            WindowCompat.setDecorFitsSystemWindows(window!!, true)
        }
    }

    private fun setUpArcGISMap() {
        with(binding.mvArcgis) {
            map = mapArcgis
            val viewPoint = if (lat == -1.0 || long == -1.0) {
                Viewpoint(
                    -6.921215224408984,
                    107.61099648241901,
                    50000.0
                )
            } else {
                Viewpoint(
                    lat,
                    long,
                    50000.0
                )
            }
            setViewpoint(viewPoint)
        }

        loadRuasJalanNasional()
        loadRuasJalanProvinsi()
        loadRuasJalanKabKota()
        loadPaketPekerjaan()
    }

    private fun loadRuasJalanNasional() {
        val wfsFeatureTable =
            ArcGISMapUtils.createWfsFeatureTable(tableName = "temanjabar:2_rj_nasional")
        val featureLayer =
            ArcGISMapUtils.createLineFeatureLayer(
                wfsFeatureTable,
                lineColor = getColor(R.color.jalan_nasional),
            )
        binding.mvArcgis.apply {
            map.operationalLayers.add(featureLayer)
            addNavigationChangedListener {
                if (!it.isNavigating) {
                    ArcGISMapUtils.populateFromServer(
                        wfsFeatureTable,
                        binding.mvArcgis.visibleArea.extent
                    )
                }
            }
        }
    }

    private fun loadRuasJalanProvinsi() {
        val wfsFeatureTable =
            ArcGISMapUtils.createWfsFeatureTable(tableName = "temanjabar:0_rj_prov_v")
        val featureLayer =
            ArcGISMapUtils.createLineFeatureLayer(
                wfsFeatureTable,
                lineColor = getColor(R.color.jalan_provinsi),
            )
        binding.mvArcgis.apply {
            map.operationalLayers.add(featureLayer)
            addNavigationChangedListener {
                if (!it.isNavigating) {
                    ArcGISMapUtils.populateFromServer(
                        wfsFeatureTable,
                        binding.mvArcgis.visibleArea.extent
                    )
                }
            }
        }
    }

    private fun loadRuasJalanKabKota() {
        val wfsFeatureTable =
            ArcGISMapUtils.createWfsFeatureTable(tableName = "temanjabar:G_Jalan_Tarung")
        val featureLayer =
            ArcGISMapUtils.createLineFeatureLayer(
                wfsFeatureTable,
                lineColor = getColor(R.color.jalan_kota_kabupaten),
            )
        binding.mvArcgis.apply {
            map.operationalLayers.add(featureLayer)
            addNavigationChangedListener {
                if (!it.isNavigating) {
                    ArcGISMapUtils.populateFromServer(
                        wfsFeatureTable,
                        binding.mvArcgis.visibleArea.extent
                    )
                }
            }
        }
    }

    private fun loadPaketPekerjaan() {
        val wfsFeatureTable =
            ArcGISMapUtils.createWfsFeatureTable(tableName = "talikuat:paket_pekerjaan")
        val featureLayer =
            ArcGISMapUtils.createPointFeatureLayer(
                wfsFeatureTable,
                ArcGISMapUtils.createPictureMarker(IMAGE_LUBANG)
            )
        binding.mvArcgis.apply {
            map.operationalLayers.add(featureLayer)
            addNavigationChangedListener {
                if (!it.isNavigating) {
                    ArcGISMapUtils.populateFromServer(
                        wfsFeatureTable,
                        binding.mvArcgis.visibleArea.extent
                    )
                }
            }
        }
    }

    private fun loadLubang() {
        val wfsFeatureTable =
            ArcGISMapUtils.createWfsFeatureTable(tableName = "temanjabar:sapu_lubang_unhandled")
        val featureLayer =
            ArcGISMapUtils.createPointFeatureLayer(
                wfsFeatureTable,
                ArcGISMapUtils.createPictureMarker(IMAGE_LUBANG)
            )
        wfsFeatureTable.addDoneLoadingListener {
            Log.d(TAG, "loadPaketPekerjaan: ${wfsFeatureTable.geometryType.name}")
        }
        binding.mvArcgis.apply {
            map.operationalLayers.add(featureLayer)
            addNavigationChangedListener {
                if (!it.isNavigating) {
                    ArcGISMapUtils.populateFromServer(
                        wfsFeatureTable,
                        binding.mvArcgis.visibleArea.extent
                    )
                }
            }
        }
    }

    private fun loadLubangDirencanakan() {
        val wfsFeatureTable =
            ArcGISMapUtils.createWfsFeatureTable(tableName = "temanjabar:sapu_lubang_scheduled")
        val featureLayer =
            ArcGISMapUtils.createPointFeatureLayer(
                wfsFeatureTable,
                ArcGISMapUtils.createPictureMarker(IMAGE_LUBANG_DIRENCANAKAN)
            )
        binding.mvArcgis.apply {
            map.operationalLayers.add(featureLayer)
            addNavigationChangedListener {
                if (!it.isNavigating) {
                    ArcGISMapUtils.populateFromServer(
                        wfsFeatureTable,
                        binding.mvArcgis.visibleArea.extent
                    )
                }
            }
        }
    }

    private fun loadLubangDitangani() {
        val wfsFeatureTable =
            ArcGISMapUtils.createWfsFeatureTable(tableName = "temanjabar:sapu_lubang_done")
        val featureLayer =
            ArcGISMapUtils.createPointFeatureLayer(
                wfsFeatureTable,
                ArcGISMapUtils.createPictureMarker(IMAGE_LUBANG_SELESAI)
            )
        binding.mvArcgis.apply {
            map.operationalLayers.add(featureLayer)
            addNavigationChangedListener {
                if (!it.isNavigating) {
                    ArcGISMapUtils.populateFromServer(
                        wfsFeatureTable,
                        binding.mvArcgis.visibleArea.extent
                    )
                }
            }
        }
    }

    private fun initLocation() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                if (!locationUtils.isLocationEnabled()) {
                    locationUtils.enableLocationService()
                }
                locationDisplay.autoPanMode = LocationDisplay.AutoPanMode.RECENTER
                locationDisplay.startAsync()
            }
        }
    }

    override fun onPause() {
        mapView.pause()
        setEdgeToEdgeDisplay(false)
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        setEdgeToEdgeDisplay(true)
        mapView.resume()
    }

    override fun onDestroy() {
        mapView.dispose()
        super.onDestroy()
    }

    private companion object {
        const val IMAGE_LUBANG = "https://tj.temanjabar.net/assets/images/marker/sapulobang.png"
        const val IMAGE_LUBANG_DIRENCANAKAN =
            "https://tj.temanjabar.net/assets/images/marker/sapulobang_schedule.png"
        const val IMAGE_LUBANG_SELESAI =
            "https://tj.temanjabar.net/assets/images/marker/sapulobang_finish.png"
    }
}