package com.side_project.weatherapp.domain.usecase.forecast

import com.side_project.weatherapp.data.repository.ForecastRepositoryImpl
import com.side_project.weatherapp.domain.model.Forecast
import com.side_project.weatherapp.core.common.Resource
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(private val forecastRepositoryImpl: ForecastRepositoryImpl) {
    suspend fun getForecast(latitude: Double, longitude: Double): Resource<Forecast> =
        forecastRepositoryImpl.getForecastData(latitude, longitude)
}