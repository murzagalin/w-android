package com.github.murzagalin.restaurants.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.murzagalin.restaurants.ui.theme.RestaurantsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VenuesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RestaurantsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(
    viewModel: VenuesViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val venuesState = viewModel.venuesFlow.collectAsState()
    when (val state = venuesState.value) {
        is VenuesViewModel.ViewState.Loading -> {
            Text(
                text = "Loading",
                modifier = modifier
            )
        }
        is VenuesViewModel.ViewState.Success -> {
            Text(
                text = "Got venues: ${state.venuesData.name}: ${state.venuesData.venues.size}",
                modifier = modifier
            )
        }
        is VenuesViewModel.ViewState.Error -> {
            Text(
                text = "Error: ${state.error}",
                modifier = modifier
            )
        }

        VenuesViewModel.ViewState.Empty -> {}
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RestaurantsTheme {
    }
}