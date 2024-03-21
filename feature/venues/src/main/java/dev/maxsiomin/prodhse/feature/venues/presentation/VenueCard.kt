package dev.maxsiomin.prodhse.feature.venues.presentation

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import dev.maxsiomin.prodhse.feature.venues.domain.PlaceModel

@Composable
fun VenueCard(placeModel: PlaceModel) {

    Text(text = placeModel.toString())
    HorizontalDivider()

}