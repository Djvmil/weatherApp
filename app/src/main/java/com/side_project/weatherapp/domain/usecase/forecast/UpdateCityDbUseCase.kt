package com.side_project.weatherapp.domain.usecase.forecast

import com.side_project.weatherapp.data.repository.ForecastRepositoryImpl
import com.side_project.weatherapp.domain.model.City
import javax.inject.Inject

class UpdateCityDbUseCase @Inject constructor(private val forecastRepositoryImpl: ForecastRepositoryImpl) {
    suspend fun updateCityDb(city: City) = forecastRepositoryImpl.updateCity(city)
}