package com.github.murzagalin.restaurants

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.github.murzagalin.restaurants.data.FavoritesStorage
import com.github.murzagalin.restaurants.data.RetrofitBuilder
import com.github.murzagalin.restaurants.data.VenuesApi
import com.github.murzagalin.restaurants.data.VenuesApiMapper
import com.github.murzagalin.restaurants.data.VenuesRepository
import com.github.murzagalin.restaurants.domain.GetLocationsUseCase
import com.github.murzagalin.restaurants.domain.GetVenuesUseCase
import com.github.murzagalin.restaurants.domain.LocationCoordinates
import com.github.murzagalin.restaurants.ui.theme.RestaurantsTheme
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    val api by lazy { RetrofitBuilder.build(VenuesApi::class.java) }
    val favoritesStorage by lazy { FavoritesStorage(applicationContext) }
    val venuesMapper by lazy { VenuesApiMapper() }

    val useCase by lazy { GetVenuesUseCase(venuesRepository = VenuesRepository(api, favoritesStorage, venuesMapper)) }
    val locationsUseCase by lazy { GetLocationsUseCase() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RestaurantsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        lifecycleScope.launch {
            locationsUseCase()
                .map { location ->
                    Log.d("MainActivity", "Location: $location")
                    useCase(location)
                }.collect { venues ->
                    Log.d("MainActivity", "Venues: $venues")
                }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RestaurantsTheme {
        Greeting("Android")
    }
}