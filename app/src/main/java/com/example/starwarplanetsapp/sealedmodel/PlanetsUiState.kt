package com.example.starwarplanetsapp.sealedmodel

import com.example.starwarplanetsapp.dataclass.Planets

sealed interface PlanetsUiState {
    object Loading : PlanetsUiState
    data class Success(val planets: List<Planets>) : PlanetsUiState
    data class Error(val message: String) : PlanetsUiState
}