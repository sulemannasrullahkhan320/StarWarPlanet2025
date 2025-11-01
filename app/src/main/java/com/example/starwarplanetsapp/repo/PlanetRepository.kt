package com.example.starwarplanetsapp.repo

import android.util.Log
import com.example.starwarplanetsapp.dataclass.Planets
import com.example.starwarplanetsapp.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlanetRepository(private val api: ApiService) {

    suspend fun getPlanets(): Result<List<Planets>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getPlanets()
            val planets = response.results.map { dto ->
                val id = dto.url.trimEnd('/').split("/").last()
                Planets(
                    id = id,
                    name = dto.name,
                    climate = dto.climate,
                    orbitalPeriod = dto.orbital_period,
                    gravity = dto.gravity
                )
            }
            Result.success(planets)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}