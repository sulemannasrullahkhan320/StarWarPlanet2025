package com.example.starwarplanetsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarplanetsapp.repo.PlanetRepository
import com.example.starwarplanetsapp.sealedmodel.PlanetsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlanetsViewModel(private val repository: PlanetRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<PlanetsUiState>(PlanetsUiState.Loading)
    val uiState: StateFlow<PlanetsUiState> = _uiState

    init {
        loadPlanets()
    }
    fun loadPlanets() {
        viewModelScope.launch {
            _uiState.value = PlanetsUiState.Loading
            val result = repository.getPlanets()

            if (result.isSuccess) {
                val planets = result.getOrNull().orEmpty()
                _uiState.value = PlanetsUiState.Success(planets)
            } else {
                val errorMsg = result.exceptionOrNull()?.localizedMessage ?: "Error loading planets"
                _uiState.value = PlanetsUiState.Error(errorMsg)
            }
        }
    }

}