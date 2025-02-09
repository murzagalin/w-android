package com.github.murzagalin.restaurants.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.murzagalin.restaurants.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VenueScreen(
    venuesData: VenuesDataUiModel,
    isLoading: Boolean,
    setFavorite: (String, Boolean) -> Unit = { _, _ -> }
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAppBar(
                title = {
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        Text(text = venuesData.pageTitle, fontWeight = FontWeight.Bold)
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .size(16.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.primary),
                    titleContentColor = Color.White
                )
            )

            VenuesList(venuesData.venues, setFavorite)
        }
    }
}

@Composable
fun VenuesList(
    venues: List<VenueUiModel>,
    setFavorite: (String, Boolean) -> Unit
) {
    LazyColumn(
        modifier = Modifier.background(Color.White)
    ) {
        items(
            count = venues.size,
            key = { index -> venues[index].id }
        ) { index ->
            VenueItem(
                venue = venues[index],
                modifier = Modifier.animateItem(),
                setFavorite
            )

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
    VenueScreen(
        venuesData = VenuesDataUiModel(
            pageTitle = "Test Page Title",
            venues = listOf(
                VenueUiModel("1", "Venue 1", "Description 1", "ImageUrl 1", true),
                VenueUiModel("2", "Venue 2", "Description 2", "ImageUrl 2", false)
            )
        ),
        isLoading = false
    )
}

@Preview(showBackground = true)
@Composable
fun VenueScreenPreviewLoadingWithContent() {
    VenueScreen(
        venuesData = VenuesDataUiModel(
            pageTitle = "Test Page Title",
            venues = listOf(
                VenueUiModel("1", "Venue 1", "Description 1", "ImageUrl 1", true),
                VenueUiModel("2", "Venue 2", "Description 2", "ImageUrl 2", false)
            )
        ),
        isLoading = true
    )
}
