package id.go.jabarprov.dbmpr.feature.dashboard.data.repository

import id.go.jabarprov.dbmpr.core_main.either.Either
import id.go.jabarprov.dbmpr.core_main.extensions.safeDataSourceCall
import id.go.jabarprov.dbmpr.core_main.failures.Failure
import id.go.jabarprov.dbmpr.feature.dashboard.data.mapper.RuasJalanDataMapper
import id.go.jabarprov.dbmpr.feature.dashboard.data.remote.ruas_jalan.RuasJalanRemoteDataSource
import id.go.jabarprov.dbmpr.feature.dashboard.domain.entity.RuasJalan
import id.go.jabarprov.dbmpr.feature.dashboard.domain.repository.RuasJalanRepository
import javax.inject.Inject

class RuasJalanRepositoryImpl @Inject constructor(private val ruasJalanRemoteDataSource: RuasJalanRemoteDataSource) :
    RuasJalanRepository {
    override suspend fun getNearbyRuasJalan(lat: Double, long: Double): Either<Failure, RuasJalan> {
        return safeDataSourceCall {
            val result = ruasJalanRemoteDataSource.getNearbyRuasJalan(lat, long)
            RuasJalanDataMapper.convertNearbyRuasJalanResponseToEntity(result)
        }
    }
}