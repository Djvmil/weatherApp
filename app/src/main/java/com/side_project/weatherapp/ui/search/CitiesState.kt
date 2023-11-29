package com.side_project.weatherapp.ui.search

import com.side_project.weatherapp.domain.model.Forecast

sealed interface CitiesState {
    data class Success(val forecast: List<Forecast?>): CitiesState
    data class Error(val errorMessage: String?): CitiesState

    object Loading: CitiesState
}