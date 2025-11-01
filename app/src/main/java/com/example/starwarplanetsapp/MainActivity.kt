package com.example.starwarplanetsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.starwarplanetsapp.Di.appModule
import com.example.starwarplanetsapp.constant.AppConstants.ARG_CLIMATE
import com.example.starwarplanetsapp.constant.AppConstants.ARG_GRAVITY
import com.example.starwarplanetsapp.constant.AppConstants.ARG_NAME
import com.example.starwarplanetsapp.constant.AppConstants.ARG_ORBITAL
import com.example.starwarplanetsapp.constant.AppConstants.ROUTE_DETAILS
import com.example.starwarplanetsapp.constant.AppConstants.ROUTE_PLANETS
import com.example.starwarplanetsapp.constant.networkModule
import com.example.starwarplanetsapp.dataclass.Planets
import com.example.starwarplanetsapp.ui.theme.PlanetDetailsScreen
import com.example.starwarplanetsapp.ui.theme.PlanetsListScreen
import com.example.starwarplanetsapp.viewmodel.PlanetsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.getViewModel
import org.koin.core.context.startKoin
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Initialize Koin (Dependency Injection)
        startKoin {
            androidContext(this@MainActivity)
            modules(networkModule, appModule)
        }

        // ✅ Compose UI content
        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = ROUTE_PLANETS) {

                    // --- Planet list screen ---
                    composable(ROUTE_PLANETS) {
                        val viewModel: PlanetsViewModel = getViewModel()
                        PlanetsListScreen(viewModel = viewModel) { planet ->

                            val encode = { text: String -> URLEncoder.encode(text, StandardCharsets.UTF_8.toString()) }

                            val encodedRoute =
                                "$ROUTE_DETAILS/${encode(planet.name)}/${encode(planet.climate)}/${encode(planet.gravity)}/${encode(planet.orbitalPeriod)}"


                            navController.navigate(encodedRoute)
                        }
                    }

                    // --- Details screen ---
                    composable(
                        route = "$ROUTE_DETAILS/{$ARG_NAME}/{$ARG_CLIMATE}/{$ARG_GRAVITY}/{$ARG_ORBITAL}",
                        arguments = listOf(
                            navArgument(ARG_NAME) { type = NavType.StringType },
                            navArgument(ARG_CLIMATE) { type = NavType.StringType },
                            navArgument(ARG_GRAVITY) { type = NavType.StringType },
                            navArgument(ARG_ORBITAL) { type = NavType.StringType }
                        )
                    ) { backStackEntry ->

                        val args = backStackEntry.arguments!!


                        val planet = Planets(
                            id = "0",
                            name = args.getString(ARG_NAME).toString(),
                            climate = args.getString(ARG_CLIMATE).toString(),
                            gravity = args.getString(ARG_GRAVITY).toString(),
                            orbitalPeriod = args.getString(ARG_ORBITAL).toString()
                        )

                        PlanetDetailsScreen(planets = planet) {
                            navController.popBackStack()
                        }
                    }
                }
            }
        }
    }
}
