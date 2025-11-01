package com.example.starwarplanetsapp

import com.example.starwarplanetsapp.dataclass.PlanetsResponseData
import retrofit2.http.GET

interface ApiService {
    @GET("planets")
    suspend fun getPlanets(): PlanetsResponseData
}