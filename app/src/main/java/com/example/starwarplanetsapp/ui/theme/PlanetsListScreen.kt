package com.example.starwarplanetsapp.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.starwarplanetsapp.constant.AppConstants.PICSUM_URL
import com.example.starwarplanetsapp.dataclass.Planets
import com.example.starwarplanetsapp.sealedmodel.PlanetsUiState
import com.example.starwarplanetsapp.viewmodel.PlanetsViewModel

@Composable
fun PlanetsListScreen(
    viewModel: PlanetsViewModel,
    onPlanetClick: (Planets) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    when (state) {
        is PlanetsUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is PlanetsUiState.Error -> {
            val msg = (state as PlanetsUiState.Error).message
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = msg, textAlign = TextAlign.Center)
                    Spacer(Modifier.height(7.dp))
                    Button(onClick = { viewModel.loadPlanets() }) {
                        Text("Retry")
                    }
                }
            }
        }

        is PlanetsUiState.Success -> {
            val planets = (state as PlanetsUiState.Success).planets
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(planets) { planet ->
                    PlanetCard(planets = planet, onClick = { onPlanetClick(planet) })
                }
            }
        }
    }
}

@Composable
fun PlanetCard(planets: Planets, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(modifier = Modifier.height(120.dp)) {
            val imageUrl = "$PICSUM_URL${planets.name.replace(" ", "%20")}/300/180"
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .width(140.dp)
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(planets.name, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(6.dp))
                Text("Climate: ${planets.climate}")
            }
        }
    }
}
