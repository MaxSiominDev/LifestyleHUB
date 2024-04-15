package dev.maxsiomin.prodhse.feature.home.presentation.home_tld.home

import dev.maxsiomin.prodhse.feature.home.domain.PlaceModel

sealed class HomeFeedItem {
    data object Weather : HomeFeedItem()
    data class Place(val placeModel: PlaceModel) : HomeFeedItem()
}