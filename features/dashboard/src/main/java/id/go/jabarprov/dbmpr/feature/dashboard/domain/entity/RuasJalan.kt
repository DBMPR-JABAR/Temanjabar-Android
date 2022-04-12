package id.go.jabarprov.dbmpr.feature.dashboard.domain.entity

data class RuasJalan(
    val id: String,
    val nama: String,
    val panjang: Double,
    val staAwal: String,
    val staAkhir: String,
    val latAwal: Double,
    val latAkhir: Double,
    val longAwal: Double,
    val longAkhir: Double,
    val kota: String,
)
