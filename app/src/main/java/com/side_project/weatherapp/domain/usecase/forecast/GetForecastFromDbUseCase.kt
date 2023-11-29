package com.side_project.weatherapp.domain.usecase.forecast

import com.side_project.weatherapp.data.repository.ForecastRepositoryImpl
import com.side_project.weatherapp.domain.model.Forecast
import javax.inject.Inject

class GetForecastFromDbUseCase @Inject constructor(private val forecastRepositoryImpl: ForecastRepositoryImpl) {
    fun getForecastFromDbUseCase() : Forecast? = forecastRepositoryImpl.getForecastWeather()
}