package dev.maxsiomin.prodhse.feature.home.presentation.planner_tld.planner

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.maxsiomin.prodhse.core.ui.theme.ProdhseTheme
import dev.maxsiomin.prodhse.feature.home.domain.Photo
import dev.maxsiomin.prodhse.feature.home.domain.PlaceDetails
import dev.maxsiomin.prodhse.feature.home.domain.Plan
import dev.maxsiomin.prodhse.feature.home.R

@Composable
internal fun PlanCard(placeDetails: PlaceDetails, plan: Plan, onClick: () -> Unit) {

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

            val imageModifier = Modifier
                .fillMaxWidth()
                .height(200.dp)

            if (isPreview) {
                Image(
                    modifier = imageModifier,
                    painter = painterResource(id = R.drawable.place_preview),
                    contentDescription = null,
                )
            } else {
                placeDetails.photos.firstOrNull()?.url?.let { url ->
                    AsyncImage(
                        model = url,
                        contentDescription = stringResource(id = R.string.attraction_image),
                        modifier = imageModifier,
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            if (plan.noteTitle.isNotBlank()) {
                Text(text = plan.noteTitle, fontWeight = FontWeight.Bold)
            }
            Text(text = plan.dateString, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
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
            placeDetails = PlaceDetails(
                name = "Кафе Studio 89.5",
                address = "Маросейка, д. 13, 101000, Москва",
                photos = listOf(
                    Photo(id = "", url = ""),
                    Photo(id = "", url = ""),
                    Photo(id = "", url = ""),
                    Photo(id = "", url = ""),
                    Photo(id = "", url = ""),
                    Photo(id = "", url = ""),
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
                email = "example@example.com",
                phone = "+79291234576",
            ),
            onClick = {},
            plan = Plan(
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