package id.go.jabarprov.dbmpr.feature.dashboard.domain.usecase

import id.go.jabarprov.dbmpr.core_main.either.Either
import id.go.jabarprov.dbmpr.core_main.failures.Failure
import id.go.jabarprov.dbmpr.core_main.usecase.UseCase
import id.go.jabarprov.dbmpr.feature.dashboard.domain.entity.RuasJalan
import id.go.jabarprov.dbmpr.feature.dashboard.domain.repository.RuasJalanRepository
import javax.inject.Inject

class GetNearbyRuasJalan @Inject constructor(private val ruasJalanRepository: RuasJalanRepository) :
    UseCase<RuasJalan, GetNearbyRuasJalan.Params>() {

    data class Params(
        val lat: Double,
        val long: Double
    )

    override suspend fun run(params: Params): Either<Failure, RuasJalan> {
        return ruasJalanRepository.getNearbyRuasJalan(params.lat, params.long)
    }
}