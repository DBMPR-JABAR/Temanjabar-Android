package id.go.jabarprov.dbmpr.feature.dashboard.data.mapper

import id.go.jabarprov.dbmpr.feature.dashboard.data.models.remote.response.NearbyRuasJalanResponse
import id.go.jabarprov.dbmpr.feature.dashboard.domain.entity.RuasJalan

abstract class RuasJalanDataMapper {
    companion object {
        fun convertNearbyRuasJalanResponseToEntity(nearbyRuasJalanResponse: NearbyRuasJalanResponse): RuasJalan {
            return RuasJalan(
                id = nearbyRuasJalanResponse.idRuasJalan,
                nama = nearbyRuasJalanResponse.ruas.namaRuasJalan,
                panjang = nearbyRuasJalanResponse.ruas.panjang,
                staAwal = nearbyRuasJalanResponse.ruas.staAwal,
                staAkhir = nearbyRuasJalanResponse.ruas.staAkhir,
                latAwal = nearbyRuasJalanResponse.ruas.latAwal,
                latAkhir = nearbyRuasJalanResponse.ruas.latAkhir,
                longAwal = nearbyRuasJalanResponse.ruas.longAwal,
                longAkhir = nearbyRuasJalanResponse.ruas.longAkhir,
                kota = nearbyRuasJalanResponse.ruas.kota ?: "-"
            )
        }
    }
}