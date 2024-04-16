package dev.maxsiomin.prodhse.feature.home.data.mappers

import dev.maxsiomin.prodhse.feature.home.data.dto.place_details.PlaceDetailsResponse
import dev.maxsiomin.prodhse.feature.home.domain.Photo
import dev.maxsiomin.prodhse.feature.home.domain.PlaceDetails

internal class PlaceDetailsDtoToUoModelMapper : (PlaceDetailsResponse) -> PlaceDetails? {

    override fun invoke(detailsDto: PlaceDetailsResponse): PlaceDetails? {
        val timeUpdated = System.currentTimeMillis()
        val category = detailsDto.categories?.mapNotNull { it?.name }
        if (category.isNullOrEmpty()) return null
        val address = detailsDto.location?.formattedAddress ?: return null
        val name = detailsDto.name ?: return null
        val id = detailsDto.fsqId ?: return null
        val rating = detailsDto.rating
        val website = detailsDto.website
        val isVerified = detailsDto.verified
        val workingHours = detailsDto.hours?.display?.split(";")?.map { it.trim() }
        val isOpenNow = detailsDto.hours?.openNow
        val photos = detailsDto.photos?.mapNotNull {
            it?.let {
                Photo(id = id, url = "${it.prefix}original${it.suffix}")
            }
        } ?: emptyList()
        val phone = detailsDto.phone
        val email = detailsDto.email

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
            fsqId = id,
            phone = phone,
            email = email,
        )
    }

}