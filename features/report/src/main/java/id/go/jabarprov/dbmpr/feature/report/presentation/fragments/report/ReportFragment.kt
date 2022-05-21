package id.go.jabarprov.dbmpr.feature.report.presentation.fragments.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.esri.arcgisruntime.geometry.Point
import com.esri.arcgisruntime.geometry.SpatialReferences
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.BasemapStyle
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.Graphic
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay
import com.esri.arcgisruntime.symbology.SimpleLineSymbol
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol
import id.go.jabarprov.dbmpr.feature.report.databinding.FragmentReportBinding
import id.go.jabarprov.dbmpr.feature.report.presentation.adapters.SlidePhotoAdapter
import id.go.jabarprov.dbmpr.feature.report.presentation.models.ReportModel
import id.go.jabarprov.dbmpr.core_views.R as CoreR

class ReportFragment : Fragment() {

    private val listSlidePhotoAdapter by lazy { SlidePhotoAdapter(LIST_REPORT) }

    private lateinit var binding: FragmentReportBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initMap()
    }

    private fun initUI() {
        binding.apply {
            viewPagerSlidePhoto.apply {
                adapter = listSlidePhotoAdapter
            }
            dotIndicator.attachTo(viewPagerSlidePhoto)
        }
    }

    private fun initMap() {
        binding.mapView.apply {
            map = ArcGISMap(BasemapStyle.ARCGIS_NAVIGATION)
            setViewpoint(
                Viewpoint(
                    -6.921215224408984,
                    107.61099648241901,
                    10000.0
                )
            )

            val graphicOvelay = GraphicsOverlay()
            graphicsOverlays.add(graphicOvelay)

            val point = Point(
                107.61084913756763, // Latitude
                -6.921586855118597, // Longitude
                SpatialReferences.getWgs84(),
            )

            // create an opaque orange (0xFFFF5733) point symbol with a blue (0xFF0063FF) outline symbol
            val redColor = requireContext().getColor(CoreR.color.red_600)
            val whiteColor = requireContext().getColor(CoreR.color.white)
            val simpleMarkerSymbol =
                SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, redColor, 16f)

            val blueOutlineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, whiteColor, 2f)
            simpleMarkerSymbol.outline = blueOutlineSymbol

            // create a graphic with the point geometry and symbol
            val pointGraphic = Graphic(point, simpleMarkerSymbol)

            // add the point graphic to the graphics overlay
            graphicOvelay.graphics.add(pointGraphic)
        }
    }

    companion object {
        val LIST_REPORT = listOf(
            ReportModel("https://thumbs.dreamstime.com/b/dangerous-pothole-asphalt-rural-road-damage-149107515.jpg "),
            ReportModel("https://www.groundup.org.za/media/uploads/images/photographers/Chris%20Gilili/20180613_124236.jpg"),
            ReportModel("https://previews.123rf.com/images/gwhitton/gwhitton1006/gwhitton100600011/7589305-dommages-road.jpg"),
            ReportModel("https://www.abc.net.au/news/image/10789974-3x2-940x627.jpg"),
            ReportModel("https://images.mktw.net/im-310484?width=700&height=492"),
        )
    }

}