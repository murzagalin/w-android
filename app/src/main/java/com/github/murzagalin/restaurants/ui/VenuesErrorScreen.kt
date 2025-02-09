package com.github.murzagalin.restaurants.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.murzagalin.restaurants.R
import com.github.murzagalin.restaurants.data.NetworkException

@Composable
fun VenuesErrorScreen(
    error: Throwable,
    modifier: Modifier = Modifier
) {
    val imgResource = when (error) {
        is NetworkException -> R.drawable.img_no_internet
        else -> R.drawable.img_error_outline
    }

    val text = when (error) {
        is NetworkException -> "No internet. Please check your internet connection."
        else -> "An unexpected error happened."
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imgResource),
                colorFilter = ColorFilter.tint(colorResource(R.color.error)),
                contentDescription = "Error Image",
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = text,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VenuesErrorScreenPreview() {
    VenuesErrorScreen(
        error = NetworkException("No internet connection")
    )
}

@Preview(showBackground = true)
@Composable
fun VenuesErrorScreenGenericErrorPreview() {
    VenuesErrorScreen(
        error = Exception("Generic error")
    )
}