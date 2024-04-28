package dev.maxsiomin.prodhse.feature.home.presentation.home_tld.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.maxsiomin.common.presentation.components.grayShimmerColors
import dev.maxsiomin.common.presentation.components.shimmerEffect
import dev.maxsiomin.prodhse.core.presentation.theme.ProdhseTheme
import dev.maxsiomin.prodhse.feature.home.domain.model.Place
import dev.maxsiomin.prodhse.feature.home.R

@Composable
internal fun PlaceCard(place: Place, goToDetails: () -> Unit, addToPlans: () -> Unit) {

    val isPreview = LocalInspectionMode.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                goToDetails()
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {

            val imageModifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
            if (place.photoUrl == null) {
                Box(modifier = imageModifier.shimmerEffect(grayShimmerColors))
            } else {
                if (isPreview) {
                    Image(
                        modifier = imageModifier,
                        painter = painterResource(id = R.drawable.place_preview),
                        contentDescription = null,
                    )
                } else {
                    AsyncImage(
                        model = place.photoUrl,
                        contentDescription = stringResource(R.string.attraction_image),
                        modifier = imageModifier,
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = place.name, fontWeight = FontWeight.Bold)
            Text(text = place.address)

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                Text(text = place.categories, modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {
                        addToPlans()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AddCircleOutline,
                        contentDescription = stringResource(id = R.string.add_to_plans)
                    )
                }
            }
        }
    }

}

@Preview
@Composable
private fun VenueCardPreview() {

    ProdhseTheme {
        PlaceCard(
            place = Place(
                name = "Dodo Pizza",
                address = "16, Odesskaya st., Moscow, Russia",
                photoUrl = "",
                fsqId = "",
                categories = "Pizza, Restaurants",
            ),
            {},
            {},
        )
    }

}