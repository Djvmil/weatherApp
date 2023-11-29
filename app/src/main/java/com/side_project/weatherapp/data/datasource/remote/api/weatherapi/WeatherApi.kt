package com.side_project.weatherapp.data.datasource.remote.api.weatherapi

import com.side_project.weatherapp.data.datasource.remote.api.entity.ForecastDto
import com.side_project.weatherapp.core.utils.NetworkService
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET(NetworkService.FORECAST_END_POINT)
    suspend fun getForecastData(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("APPID") apiKey: String = NetworkService.API_KEY,
        @Query("units") units: String = NetworkService.UNITS,
        @Query("lang") lang: String = NetworkService.LANG,
    ): ForecastDto

    @GET(NetworkService.FORECAST_END_POINT)
    suspend fun getForecastDataWithCityName(
        @Query("q") cityName: String,
        @Query("APPID") apiKey: String = NetworkService.API_KEY,
        @Query("units") units: String = NetworkService.UNITS,
        @Query("lang") lang: String = NetworkService.LANG,
    ): ForecastDto
}