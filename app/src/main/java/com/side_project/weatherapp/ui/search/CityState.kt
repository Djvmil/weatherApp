package com.side_project.weatherapp.ui.search

import com.side_project.weatherapp.domain.model.Forecast

sealed interface CityState {
    data class Success(val forecast: Forecast?): CityState
    data class Error(val errorMessage: String?): CityState

    object Loading: CityState
}