package dev.maxsiomin.prodhse.feature.venues.presentation.planner

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import dev.maxsiomin.prodhse.core.ui.theme.ProdhseTheme
import dev.maxsiomin.prodhse.feature.venues.R
import dev.maxsiomin.prodhse.feature.venues.domain.PhotoModel
import dev.maxsiomin.prodhse.feature.venues.domain.PlaceDetailsModel
import dev.maxsiomin.prodhse.feature.venues.domain.PlanModel
import java.time.LocalDate

@Composable
internal fun PlanCard(placeDetails: PlaceDetailsModel?, plan: PlanModel, onClick: () -> Unit) {

    val isPreview = LocalInspectionMode.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                onClick()
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {

            val modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)

            if (isPreview) {
                Image(
                    modifier = modifier,
                    painter = painterResource(id = R.drawable.place_preview),
                    contentDescription = null,
                )
            } else {
                placeDetails?.photos?.firstOrNull()?.url?.let { url ->
                    AsyncImage(
                        model = url,
                        contentDescription = "Attraction Image",
                        modifier = modifier,
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = plan.noteTitle, fontWeight = FontWeight.Bold)
            Text(text = plan.dateString, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            if (placeDetails == null) {
                Text(text = "ERROR")
                return@Column
            }
            Text(text = placeDetails.name, fontWeight = FontWeight.Bold)
            Text(text = placeDetails.address)
            Text(text = placeDetails.categories)
        }
    }

}

@Preview
@Composable
private fun VenueCardPreview() {

    ProdhseTheme {
        PlanCard(
            placeDetails = PlaceDetailsModel(
                name = "Кафе Studio 89.5",
                address = "Маросейка, д. 13, 101000, Москва",
                photos = listOf(
                    PhotoModel(id = "", url = ""),
                    PhotoModel(id = "", url = ""),
                    PhotoModel(id = "", url = ""),
                    PhotoModel(id = "", url = ""),
                    PhotoModel(id = "", url = ""),
                    PhotoModel(id = "", url = ""),
                ),
                workingHours = listOf(
                    "Mon-Thu 11:00-23:00",
                    "Fri 11:00-24:00",
                    "Sat-Sun 0:00-2:00",
                ),
                isVerified = true,
                rating = 9.3,
                website = "https://megapolism.ru",
                isOpenNow = true,
                fsqId = "",
                categories = "Cafe",
                timeUpdated = System.currentTimeMillis(),
            ),
            onClick = {},
            plan = PlanModel(
                databaseId = 0,
                placeFsqId = "",
                noteTitle = "Go to museum with rokymiel",
                noteText = "",
                dateString = "March 27, 2024",
                date = 0
            )
        )
    }

}