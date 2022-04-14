package id.go.jabarprov.dbmpr.feature.dashboard.data.services

import id.go.jabarprov.dbmpr.common.data.models.remote.response.BaseResponse
import id.go.jabarprov.dbmpr.feature.dashboard.data.models.remote.response.NearbyRuasJalanResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RuasJalanAPI {
    @GET("nearby/ruas/{lat}/{long}")
    suspend fun getNearbyRuasJalan(
        @Path("lat") lat: Double,
        @Path("long") long: Double
    ): Response<BaseResponse<NearbyRuasJalanResponse>>
}