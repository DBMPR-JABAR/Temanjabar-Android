package id.go.jabarprov.dbmpr.feature.dashboard.domain.repository

import id.go.jabarprov.dbmpr.core_main.either.Either
import id.go.jabarprov.dbmpr.core_main.failures.Failure
import id.go.jabarprov.dbmpr.feature.dashboard.domain.entity.RuasJalan

interface RuasJalanRepository {
    suspend fun getNearbyRuasJalan(lat: Double, long: Double): Either<Failure, RuasJalan>
}