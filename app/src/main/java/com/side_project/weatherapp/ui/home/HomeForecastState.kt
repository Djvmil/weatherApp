package com.side_project.weatherapp.ui.home

import com.side_project.weatherapp.domain.model.Forecast

sealed interface HomeForecastState {
    data class Success(val forecast: Forecast?): HomeForecastState
    data class Error(val errorMessage: String?): HomeForecastState

    object Loading: HomeForecastState
}