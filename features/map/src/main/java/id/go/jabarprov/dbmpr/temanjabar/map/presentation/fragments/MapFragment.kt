package id.go.jabarprov.dbmpr.temanjabar.map.presentation.fragments

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.BasemapStyle
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.LocationDisplay
import com.google.android.material.bottomsheet.BottomSheetBehavior
import id.go.jabarprov.dbmpr.temanjabar.feature.map.R
import id.go.jabarprov.dbmpr.temanjabar.feature.map.databinding.FragmentMapBinding
import id.go.jabarprov.dbmpr.temanjabar.map.utils.ArcGISMapUtils
import id.go.jabarprov.dbmpr.utils.extensions.checkPermission
import id.go.jabarprov.dbmpr.utils.utils.LocationUtils
import kotlinx.coroutines.launch
import id.go.jabarprov.dbmpr.core_views.R as CoreR

private const val TAG = "MapFragment"

class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding

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

    private val locationUtils by lazy { LocationUtils(requireActivity()) }

    private val bottomSheetBehaviour by lazy {
        BottomSheetBehavior.from(binding.bottomSheet)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
                findNavController().popBackStack()
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
            requireActivity().window?.statusBarColor =
                ContextCompat.getColor(requireContext(), android.R.color.transparent)
            WindowCompat.setDecorFitsSystemWindows(activity?.window!!, false)
            ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, windowInsets ->
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
                binding.buttonBack.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    topMargin = insets.top + 32
                }
                WindowInsetsCompat.CONSUMED
            }
        } else {
            requireActivity().window?.statusBarColor =
                ContextCompat.getColor(requireContext(), CoreR.color.white)
            WindowCompat.setDecorFitsSystemWindows(activity?.window!!, true)
        }
    }

    private fun setUpArcGISMap() {
        binding.mvArcgis.apply {
            map = mapArcgis
            val viewPoint = if (arguments?.get("lat") == null || arguments?.get("long") == null) {
                Viewpoint(
                    -6.921215224408984,
                    107.61099648241901,
                    50000.0
                )
            } else {
                Viewpoint(
                    arguments?.get("lat").toString().toDouble(),
                    arguments?.get("long").toString().toDouble(),
                    50000.0
                )
            }
            setViewpoint(viewPoint)
        }

        loadRuasJalanNasional()
        loadRuasJalanProvinsi()
        loadRuasJalanKabKota()
    }

    private fun loadRuasJalanNasional() {
        val wfsFeatureTable =
            ArcGISMapUtils.createWfsFeatureTable(tableName = "temanjabar:2_rj_nasional")
        val featureLayer =
            ArcGISMapUtils.createLineFeatureLayer(
                wfsFeatureTable,
                lineColor = requireContext().getColor(R.color.jalan_nasional),
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
                lineColor = requireContext().getColor(R.color.jalan_provinsi),
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
                lineColor = requireContext().getColor(R.color.jalan_kota_kabupaten),
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
}