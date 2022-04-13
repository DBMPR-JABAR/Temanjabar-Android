package id.go.jabarprov.dbmpr.temanjabar.map.presentation.fragments

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.esri.arcgisruntime.data.QueryParameters
import com.esri.arcgisruntime.data.ServiceFeatureTable
import com.esri.arcgisruntime.geometry.Envelope
import com.esri.arcgisruntime.layers.FeatureLayer
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.BasemapStyle
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.LocationDisplay
import com.esri.arcgisruntime.ogc.wfs.WfsFeatureTable
import com.esri.arcgisruntime.symbology.SimpleLineSymbol
import com.esri.arcgisruntime.symbology.SimpleRenderer
import id.go.jabarprov.dbmpr.temanjabar.feature.map.databinding.FragmentMapBinding
import id.go.jabarprov.dbmpr.utils.extensions.checkPermission
import id.go.jabarprov.dbmpr.utils.utils.LocationUtils
import kotlinx.coroutines.launch

private const val TAG = "MapFragment"

class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding

    private val mapView by lazy { binding.mvArcgis }

    private val locationDisplay by lazy { mapView.locationDisplay }

    private val locationUtils by lazy { LocationUtils(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
        if (checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION) && checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            initLocation()
        }
    }

    private fun initUI() {
        binding.apply {
            appBarLayout.buttonBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        setUpArcGISMap()
    }

    private fun setUpArcGISMap() {
        val map = ArcGISMap(BasemapStyle.ARCGIS_NAVIGATION)
        binding.mvArcgis.map = map
        arguments?.let {
            binding.mvArcgis.setViewpoint(
                Viewpoint(
                    it.get("lat").toString().toDouble(),
                    it.get("long").toString().toDouble(),
                    72000.0
                )
            )
        }

        // Simple renderer for override style in feature layer
        val lineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.rgb(0, 0, 255), 2f)
        val simpleRenderer = SimpleRenderer(lineSymbol)

        val wfsFeatureTable = WfsFeatureTable(
            "https://geo.temanjabar.net/geoserver/wfs?service=wfs&version=2.0.0&request=GetCapabilities",
            "temanjabar:0_rj_prov",
        )

        wfsFeatureTable.featureRequestMode = ServiceFeatureTable.FeatureRequestMode.MANUAL_CACHE

        val featureLayer = FeatureLayer(wfsFeatureTable)
        featureLayer.renderer = simpleRenderer

        featureLayer.addDoneLoadingListener {
            Log.d(TAG, "setUpArcGISMap: ${featureLayer.loadStatus}")
            if (featureLayer.loadError != null) {
                Log.d(TAG, "setUpArcGISMap: ERROR ${featureLayer.loadError.cause}")
            }
        }

        binding.mvArcgis.map.operationalLayers.add(featureLayer)

        binding.mvArcgis.addNavigationChangedListener {
            if (!it.isNavigating) {
                popoulateFromServer(wfsFeatureTable, binding.mvArcgis.visibleArea.extent)
            }
        }
    }

    private fun initLocation() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                if (!locationUtils.isLocationEnabled()) {
                    locationUtils.enableLocationService()
                }
                locationDisplay.autoPanMode = LocationDisplay.AutoPanMode.RECENTER
                locationDisplay.startAsync()
            }
        }
    }

    private fun popoulateFromServer(wfsFeatureTable: WfsFeatureTable, extent: Envelope) {
        val visibleExtentQuery = QueryParameters()
        visibleExtentQuery.geometry = extent
        wfsFeatureTable.populateFromServiceAsync(visibleExtentQuery, false, null)
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