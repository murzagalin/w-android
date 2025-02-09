package com.github.murzagalin.restaurants.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.github.murzagalin.restaurants.R

@Composable
fun VenueItem(
    venue: VenueUiModel,
    modifier: Modifier = Modifier,
    onFavouriteClick: (String, Boolean) -> Unit = { _, _ -> }
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .semantics(mergeDescendants = true) {
                contentDescription = "${venue.name}, ${if (venue.isFavourite) "Favourite" else "Not Favourite"}"
            },
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(venue.imageUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_img_loading),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(50.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .semantics { invisibleToUser() }
        ) {
            Text(
                text = venue.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = venue.description,
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        IconButton(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            onClick = { onFavouriteClick(venue.id, !venue.isFavourite) }
        ) {
            Icon(
                painter = painterResource(
                    id = if (venue.isFavourite)
                        R.drawable.ic_favourite_filled
                    else
                        R.drawable.ic_favourite_outline
                ),
                contentDescription = null,
                tint = if (venue.isFavourite)
                    Color.Red
                else
                    Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VenueItemPreview() {
    VenueItem(
        venue = VenueUiModel(
            id = "1",
            name = "Test Venue",
            description = "This is a test description",
            imageUrl = "",
            isFavourite = false
        )
    )
}

@Preview(showBackground = true)
@Composable
fun VenueItemFavouritePreview() {
    VenueItem(
        venue = VenueUiModel(
            id = "2",
            name = "Favourite Venue",
            description = "This is a favourite venue",
            imageUrl = "",
            isFavourite = true
        )
    )
}

@Preview(showBackground = true)
@Composable
fun VenueItemOverflowPreview() {
    VenueItem(
        venue = VenueUiModel(
            id = "1",
            name = "Test Venue with very long title",
            description = "This is a test description, that is very long to fit in 2 lines. " +
                    "If it fits, just add more text here",
            imageUrl = "",
            isFavourite = false
        )
    )
}
