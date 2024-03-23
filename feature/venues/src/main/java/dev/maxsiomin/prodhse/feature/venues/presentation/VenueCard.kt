package dev.maxsiomin.prodhse.feature.venues.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.maxsiomin.prodhse.core.ui.grayShimmerColors
import dev.maxsiomin.prodhse.core.ui.shimmerEffect
import dev.maxsiomin.prodhse.core.ui.theme.ProdhseTheme
import dev.maxsiomin.prodhse.feature.venues.R
import dev.maxsiomin.prodhse.feature.venues.domain.PlaceModel

@Composable
internal fun VenueCard(placeModel: PlaceModel) {

    val isPreview = LocalInspectionMode.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {

            val modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
            if (placeModel.photoUrl == null) {
                Box(modifier = modifier.shimmerEffect(grayShimmerColors))
            } else {
                if (isPreview) {
                    Image(
                        modifier = modifier,
                        painter = painterResource(id = R.drawable.place_preview),
                        contentDescription = null,
                    )
                } else {
                    AsyncImage(
                        model = placeModel.photoUrl,
                        contentDescription = "Attraction Image",
                        modifier = modifier,
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = placeModel.name, fontWeight = FontWeight.Bold)
            Text(text = placeModel.address)
            Text(text = placeModel.categories)
        }
    }

}

@Preview
@Composable
private fun VenueCardPreview() {

    ProdhseTheme {
        VenueCard(placeModel = PlaceModel(
            name = "Dodo Pizza",
            address = "16, Odesskaya st., Moscow, Russia",
            photoUrl = "",
            id = "",
            categories = "Pizza, Restaurants"
        ))
    }

}