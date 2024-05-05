package dev.maxsiomin.prodhse.feature.home.domain.use_case.places

import dev.maxsiomin.common.domain.resource.NetworkError
import dev.maxsiomin.common.domain.resource.Resource
import dev.maxsiomin.prodhse.core.util.LocaleManager
import dev.maxsiomin.prodhse.feature.home.domain.model.Place
import dev.maxsiomin.prodhse.feature.home.domain.repository.PlacesRepository
import javax.inject.Inject

internal class GetPlacesNearbyUseCase @Inject constructor(
    private val placesRepo: PlacesRepository,
    private val localeManager: LocaleManager
) {

    suspend operator fun invoke(lat: Double, lon: Double): Resource<List<Place>, NetworkError> {
        val lang = localeManager.getLocaleLanguage()
        return placesRepo.getPlacesNearby(lat = lat.toString(), lon = lon.toString(), lang = lang)
    }

}