package id.go.jabarprov.dbmpr.feature.report.presentation.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.esri.arcgisruntime.geometry.Point
import com.esri.arcgisruntime.geometry.SpatialReferences
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.BasemapStyle
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.Graphic
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay
import com.esri.arcgisruntime.mapping.view.MapView
import com.esri.arcgisruntime.symbology.SimpleLineSymbol
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol
import dagger.hilt.android.AndroidEntryPoint
import id.go.jabarprov.dbmpr.core_views.R
import id.go.jabarprov.dbmpr.feature.report.databinding.ActivityReportBinding
import id.go.jabarprov.dbmpr.feature.report.presentation.adapters.SlidePhotoAdapter
import id.go.jabarprov.dbmpr.feature.report.presentation.models.ReportModel

@AndroidEntryPoint
class ReportActivity : AppCompatActivity() {

    private val binding by lazy { ActivityReportBinding.inflate(layoutInflater) }

    private val listSlidePhotoAdapter by lazy { SlidePhotoAdapter(LIST_REPORT) }

    private val arcGISMap by lazy { ArcGISMap(BasemapStyle.ARCGIS_NAVIGATION) }

    private val mapViewInteractionOptions by lazy {
        MapView.InteractionOptions(binding.mapView).apply {
            isPanEnabled = false
            isRotateEnabled = false
            isZoomEnabled = false
        }
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
        initMap()
    }

    private fun initUI() {
        binding.apply {
            buttonBack.setOnClickListener {
                finish()
            }
            viewPagerSlidePhoto.apply {
                adapter = listSlidePhotoAdapter
            }
            dotIndicator.attachTo(viewPagerSlidePhoto)
        }
    }

    private fun initMap() {
        binding.mapView.apply {
            map = arcGISMap
            interactionOptions = mapViewInteractionOptions
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

            val redColor = getColor(R.color.red_600)
            val whiteColor = getColor(R.color.white)
            val simpleMarkerSymbol =
                SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, redColor, 16f)

            val blueOutlineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, whiteColor, 2f)
            simpleMarkerSymbol.outline = blueOutlineSymbol

            val pointGraphic = Graphic(point, simpleMarkerSymbol)

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