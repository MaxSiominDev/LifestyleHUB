package dev.maxsiomin.prodhse.feature.home.data.mappers

import dev.maxsiomin.prodhse.feature.home.data.dto.places_nearby.Result
import dev.maxsiomin.prodhse.feature.home.domain.Place

class PlacesDtoToUiModelMapper : (Result) -> Place? {

    override fun invoke(placeDto: Result): Place? {
        val category = placeDto.categories?.mapNotNull { it?.name }
        if (category.isNullOrEmpty()) return null
        val address = placeDto.location?.formattedAddress ?: return null
        val name = placeDto.name ?: return null
        val id = placeDto.fsqId ?: return null

        return Place(
            name = name,
            address = address,
            categories = category.joinToString(separator = ", "),
            photoUrl = null,
            fsqId = id,
        )
    }

}