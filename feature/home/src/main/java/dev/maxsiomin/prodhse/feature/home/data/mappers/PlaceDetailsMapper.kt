package dev.maxsiomin.prodhse.feature.home.data.mappers

import dev.maxsiomin.common.data.ToDomainMapper
import dev.maxsiomin.prodhse.feature.home.data.dto.place_details.PlaceDetailsResponse
import dev.maxsiomin.prodhse.feature.home.domain.model.Photo
import dev.maxsiomin.prodhse.feature.home.domain.model.PlaceDetails
import javax.inject.Inject

internal class PlaceDetailsMapper @Inject constructor() : ToDomainMapper<PlaceDetailsResponse, PlaceDetails?> {

    override fun toDomain(data: PlaceDetailsResponse): PlaceDetails? {
        val timeUpdated = System.currentTimeMillis()
        val category = data.categories?.mapNotNull { it?.name }
        if (category.isNullOrEmpty()) return null
        val address = data.location?.formattedAddress ?: return null
        val name = data.name ?: return null
        val fsqId = data.fsqId ?: return null
        val rating = data.rating
        val website = data.website
        val isVerified = data.verified
        val workingHours = data.hours?.display?.split(";")?.map { it.trim() }
        val isOpenNow = data.hours?.openNow
        val photos = data.photos?.mapNotNull {
            it?.let {
                Photo(id = fsqId, url = "${it.prefix}original${it.suffix}")
            }
        } ?: emptyList()
        val phone = data.phone
        val email = data.email

        return PlaceDetails(
            timeUpdated = timeUpdated,
            categories = category.joinToString(separator = ","),
            address = address,
            name = name,
            rating = rating,
            website = website,
            workingHours = workingHours,
            isVerified = isVerified ?: false,
            isOpenNow = isOpenNow ?: false,
            photos = photos,
            fsqId = fsqId,
            phone = phone,
            email = email,
        )
    }

}