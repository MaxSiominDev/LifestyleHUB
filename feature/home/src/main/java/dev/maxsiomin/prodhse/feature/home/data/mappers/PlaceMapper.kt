package dev.maxsiomin.prodhse.feature.home.data.mappers

import dev.maxsiomin.common.data.ToDomainMapper
import dev.maxsiomin.prodhse.feature.home.data.dto.places_nearby.Result
import dev.maxsiomin.prodhse.feature.home.domain.model.Place
import javax.inject.Inject

internal class PlaceMapper @Inject constructor(): ToDomainMapper<Result, Place?> {

    override fun toDomain(data: Result): Place? {
        val category = data.categories?.mapNotNull { it?.name }
        if (category.isNullOrEmpty()) return null
        val address = data.location?.formattedAddress ?: return null
        val name = data.name ?: return null
        val id = data.fsqId ?: return null

        return Place(
            name = name,
            address = address,
            categories = category.joinToString(separator = ", "),
            photoUrl = null,
            fsqId = id,
        )
    }

}