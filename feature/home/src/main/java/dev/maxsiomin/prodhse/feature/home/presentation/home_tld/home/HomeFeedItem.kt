package dev.maxsiomin.prodhse.feature.home.presentation.home_tld.home

sealed class HomeFeedItem {
    data object Weather : HomeFeedItem()
    data class Place(val place: dev.maxsiomin.prodhse.feature.home.domain.Place) : HomeFeedItem()
}