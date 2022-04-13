package id.go.jabarprov.dbmpr.temanjabar.map.presentation.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.esri.arcgisruntime.data.QueryParameters
import com.esri.arcgisruntime.data.ServiceFeatureTable
import com.esri.arcgisruntime.geometry.Envelope
import com.esri.arcgisruntime.layers.FeatureLayer
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.BasemapStyle
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.ogc.wfs.WfsFeatureTable
import com.esri.arcgisruntime.symbology.SimpleLineSymbol
import com.esri.arcgisruntime.symbology.SimpleRenderer
import id.go.jabarprov.dbmpr.temanjabar.feature.map.R
import id.go.jabarprov.dbmpr.temanjabar.feature.map.databinding.FragmentMapBinding

private const val TAG = "MapFragment"

class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding

    private val mapView by lazy { binding.mvArcgis }

    private val locationDisplay by lazy { mapView.locationDisplay }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
    }

    private fun initUI() {
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