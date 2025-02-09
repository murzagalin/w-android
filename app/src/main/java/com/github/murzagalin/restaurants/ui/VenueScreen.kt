package com.github.murzagalin.restaurants.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.murzagalin.restaurants.R
import com.github.murzagalin.restaurants.domain.Venue
import com.github.murzagalin.restaurants.domain.VenuesData


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VenueScreen(
    venues: VenuesData,
    setFavorite: (String, Boolean) -> Unit = { _, _ -> }
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(text = venues.pageTitle, fontWeight = FontWeight.Bold) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colorResource(id = R.color.primary),
                titleContentColor = Color.White
            )
        )

        VenuesList(venues.venues, setFavorite)
    }
}

@Composable
fun VenuesList(
    venues: List<Venue>,
    setFavorite: (String, Boolean) -> Unit
) {
    LazyColumn(
        modifier = Modifier.background(Color.White)
    ) {
        items(
            count = venues.size,
            key = { index -> venues[index].id }
        ) { index ->
            VenueItem(venues[index], setFavorite)

            if (index < venues.size - 1) {
                HorizontalDivider(
                    modifier = Modifier
                        .padding(
                            start = 74.dp,
                            end = 16.dp
                        )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VenueScreenPreview() {
    val sampleVenues = VenuesData(
        name = "Venues Name",
        pageTitle = "Sample Venues",
        venues = listOf(
            Venue(id = "1", name = "Venue 1", description = "Description 1", imageUrl = "", isFavourite = true),
            Venue(id = "2", name = "Venue 2", description = "Description 2", imageUrl = "", isFavourite = false),
            Venue(id = "3", name = "Venue 3", description = "Description 3", imageUrl = "", isFavourite = false),
            Venue(id = "4", name = "Venue 4", description = "Description 4", imageUrl = "", isFavourite = true),
            Venue(id = "5", name = "Venue 5", description = "Description 5", imageUrl = "", isFavourite = false)
        )
    )
    VenueScreen(venues = sampleVenues)
}