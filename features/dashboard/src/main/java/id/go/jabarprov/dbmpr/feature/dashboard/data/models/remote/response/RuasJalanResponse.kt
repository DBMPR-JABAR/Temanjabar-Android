package id.go.jabarprov.dbmpr.feature.dashboard.data.models.remote.response

import com.google.gson.annotations.SerializedName

data class RuasJalanResponse(
    @SerializedName("created_by") val createdBy: String?,
    @SerializedName("created_date") val createdDate: String?,
    val foto: String,
    @SerializedName("foto_1") val firstFoto: String?,
    @SerializedName("foto_2") val secondFoto: String?,
    @SerializedName("id_ruas_jalan") val idRuasJalan: String,
    val index: String,
    @SerializedName("kab_kota") val kota: String?,
    @SerializedName("kd_sppjj") val kodeSppjj: String?,
    @SerializedName("lat_akhir") val latAkhir: Double,
    @SerializedName("lat_awal") val latAwal: Double,
    val lokasi: String?,
    @SerializedName("long_akhir") val longAkhir: Double,
    @SerializedName("long_awal") val longAwal: Double,
    @SerializedName("nama_ruas_jalan") val namaRuasJalan: String,
    @SerializedName("nm_sppjj") val namaSppjj: String?,
    val panjang: Double,
    @SerializedName("sta_akhir") val staAkhir: String,
    @SerializedName("sta_awal") val staAwal: String,
    val sup: String?,
    @SerializedName("updated_by") val updatedBy: String?,
    @SerializedName("updated_date") val updatedDate: String?,
    @SerializedName("uptd_id") val idUptd: Int?,
    val video: String?,
    @SerializedName("wil_uptd") val wilayahUptd: String?
)