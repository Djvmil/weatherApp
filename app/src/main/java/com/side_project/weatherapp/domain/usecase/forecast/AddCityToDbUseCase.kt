package com.side_project.weatherapp.domain.usecase.forecast

import com.side_project.weatherapp.data.repository.ForecastRepositoryImpl
import com.side_project.weatherapp.domain.model.City
import javax.inject.Inject

class AddCityToDbUseCase @Inject constructor(private val forecastRepositoryImpl: ForecastRepositoryImpl) {
    suspend fun addCityDb(city: City) = forecastRepositoryImpl.addCity(city)
}