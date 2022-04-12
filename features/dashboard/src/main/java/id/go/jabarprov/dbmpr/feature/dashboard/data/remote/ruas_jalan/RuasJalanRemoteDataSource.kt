package id.go.jabarprov.dbmpr.feature.dashboard.data.remote.ruas_jalan

import id.go.jabarprov.dbmpr.feature.dashboard.data.models.remote.response.NearbyRuasJalanResponse

interface RuasJalanRemoteDataSource {
    suspend fun getNearbyRuasJalan(lat: Double, long: Double): NearbyRuasJalanResponse
}