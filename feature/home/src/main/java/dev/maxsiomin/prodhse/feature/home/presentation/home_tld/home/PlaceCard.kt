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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.maxsiomin.common.presentation.components.grayShimmerColors
import dev.maxsiomin.common.presentation.components.shimmerEffect
import dev.maxsiomin.prodhse.core.ui.theme.ProdhseTheme
import dev.maxsiomin.prodhse.feature.home.domain.PlaceModel
import dev.maxsiomin.prodhse.feature.home.R

@Composable
internal fun PlaceCard(placeModel: PlaceModel, goToDetails: () -> Unit, addToPlans: () -> Unit) {

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

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                Text(text = placeModel.categories, modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {
                        addToPlans()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AddCircleOutline,
                        contentDescription = "Add to plans"
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
            placeModel = PlaceModel(
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