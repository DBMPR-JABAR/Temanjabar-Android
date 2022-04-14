package id.go.jabarprov.dbmpr.feature.dashboard.data.datasource.ruas_jalan

import id.go.jabarprov.dbmpr.common.functions.safeApiCall
import id.go.jabarprov.dbmpr.feature.dashboard.data.models.remote.response.NearbyRuasJalanResponse
import id.go.jabarprov.dbmpr.feature.dashboard.data.services.RuasJalanAPI
import javax.inject.Inject

class RuasJalanRemoteDataSourceImpl @Inject constructor(private val ruasJalanAPI: RuasJalanAPI) :
    RuasJalanRemoteDataSource {
    override suspend fun getNearbyRuasJalan(lat: Double, long: Double): NearbyRuasJalanResponse {
        return safeApiCall {
            val response = ruasJalanAPI.getNearbyRuasJalan(lat, long)
            response.body()?.data!!
        }
    }
}