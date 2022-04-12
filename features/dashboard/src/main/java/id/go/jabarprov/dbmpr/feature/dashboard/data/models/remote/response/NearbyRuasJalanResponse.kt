package id.go.jabarprov.dbmpr.feature.dashboard.data.models.remote.response

import com.google.gson.annotations.SerializedName

data class NearbyRuasJalanResponse(
    @SerializedName("id_ruas_jalan") val idRuasJalan: String,
    val lat: Double,
    val long: Double,
    val distance: Double,
    val ruas: RuasJalanResponse
)