package com.example.starwarplanetsapp.Di

import com.example.starwarplanetsapp.repo.PlanetRepository
import com.example.starwarplanetsapp.viewmodel.PlanetsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { PlanetRepository(get()) }
    viewModel { PlanetsViewModel(get()) }
}