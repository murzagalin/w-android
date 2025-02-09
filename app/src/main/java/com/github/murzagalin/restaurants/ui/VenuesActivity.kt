package com.github.murzagalin.restaurants.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.murzagalin.restaurants.R
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
                    Content(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Content(
    viewModel: VenuesViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val venuesState = viewModel.venuesFlow.collectAsState()
    when (val state = venuesState.value) {
        is VenuesViewModel.ViewState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = colorResource(R.color.primary)
                )
            }
        }
        is VenuesViewModel.ViewState.Success -> {
            VenueScreen(state.venuesData)
        }
        is VenuesViewModel.ViewState.Error -> {
            VenuesErrorScreen(state.error)
        }
        VenuesViewModel.ViewState.Empty -> {}
    }
}
