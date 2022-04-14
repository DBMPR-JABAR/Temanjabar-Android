package id.go.jabarprov.dbmpr.feature.dashboard.data.datasource.ruas_jalan

import id.go.jabarprov.dbmpr.feature.dashboard.data.models.remote.response.NearbyRuasJalanResponse

interface RuasJalanRemoteDataSource {
    suspend fun getNearbyRuasJalan(lat: Double, long: Double): NearbyRuasJalanResponse
}