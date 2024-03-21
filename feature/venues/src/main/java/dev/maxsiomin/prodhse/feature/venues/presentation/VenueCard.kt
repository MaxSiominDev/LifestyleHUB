package dev.maxsiomin.prodhse.feature.venues.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.maxsiomin.prodhse.feature.venues.domain.PlaceModel
import io.ktor.websocket.Frame


@Composable
fun VenueCard(placeModel: PlaceModel) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            // Assuming you have an image called 'attraction_image.jpg' in your drawable resources
            AsyncImage(
                model = placeModel.photoUrl,
                contentDescription = "Attraction Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = placeModel.name, fontWeight = FontWeight.Bold)
            Text(text = placeModel.address)
            Text(text = placeModel.categories)
        }
    }

    HorizontalDivider()

}