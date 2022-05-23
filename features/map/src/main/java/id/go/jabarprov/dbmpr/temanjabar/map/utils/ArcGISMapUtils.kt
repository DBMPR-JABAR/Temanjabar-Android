package id.go.jabarprov.dbmpr.temanjabar.map.utils

import android.util.Log
import com.esri.arcgisruntime.data.QueryParameters
import com.esri.arcgisruntime.data.ServiceFeatureTable
import com.esri.arcgisruntime.geometry.Envelope
import com.esri.arcgisruntime.layers.FeatureLayer
import com.esri.arcgisruntime.ogc.wfs.WfsFeatureTable
import com.esri.arcgisruntime.symbology.SimpleLineSymbol
import com.esri.arcgisruntime.symbology.SimpleRenderer

private const val TAG = "ArcGISMapUtils"

abstract class ArcGISMapUtils {

    companion object {
        fun createWfsFeatureTable(
            baseUrl: String = "https://geo.temanjabar.net/geoserver/wfs?service=wfs&version=2.0.0&request=GetCapabilities",
            tableName: String,
        ): WfsFeatureTable {
            return WfsFeatureTable(
                baseUrl,
                tableName,
            ).also {
                it.featureRequestMode = ServiceFeatureTable.FeatureRequestMode.MANUAL_CACHE
            }
        }

        fun createLineFeatureLayer(
            wfsFeatureTable: WfsFeatureTable,
            lineColor: Int,
            lineWidth: Float = 5f
        ): FeatureLayer {
            val lineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, lineColor, lineWidth)
            val simpleRenderer = SimpleRenderer(lineSymbol)

            return FeatureLayer(wfsFeatureTable).also {
                it.renderer = simpleRenderer
                it.addDoneLoadingListener {
                    Log.d(TAG, "LOAD LINE LAYER (${wfsFeatureTable.tableName}) STATUS: ${it.loadStatus}")
                    if (it.loadError != null) {
                        Log.d(TAG, "LINE LAYER (${wfsFeatureTable.tableName}) ERROR STATUS: ${it.loadError.cause}")
                    }
                }
            }
        }

        fun populateFromServer(wfsFeatureTable: WfsFeatureTable, extent: Envelope) {
            val visibleExtentQuery = QueryParameters()
            visibleExtentQuery.geometry = extent
            wfsFeatureTable.populateFromServiceAsync(visibleExtentQuery, false, null)
        }
    }
}