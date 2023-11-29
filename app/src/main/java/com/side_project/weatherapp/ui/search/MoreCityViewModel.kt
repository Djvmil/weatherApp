package com.side_project.weatherapp.ui.search

import android.util.Log
import androidx.compose.ui.res.stringArrayResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.side_project.weatherapp.domain.usecase.forecast.GetForecastWithCityNameUseCase
import com.side_project.weatherapp.core.common.Resource
import com.side_project.weatherapp.core.utils.CITIES
import com.side_project.weatherapp.domain.model.Forecast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class MoreCityViewModel @Inject constructor(
    private val getForecastWithCityName: GetForecastWithCityNameUseCase,
) : ViewModel() {

    private val _cities = MutableStateFlow<MutableList<Forecast>>(arrayListOf())
    val cities = _cities.asStateFlow()

    private val _waitingMsg = MutableStateFlow("")
    val waitingMsg = _waitingMsg.asStateFlow()

    private val _cityState = MutableStateFlow<CityState>(CityState.Loading)
    val cityState = _cityState.asStateFlow()

    private val _percentage = MutableStateFlow(0F)
    val percentage = _percentage.asStateFlow()


    fun makeRequests() {
        viewModelScope.launch(Dispatchers.IO){
            _percentage.value = 0F
            _cities.value.clear()
            repeat(CITIES.values().size) {
                try {
                    fetchForecastWithCityName(CITIES.values()[it].name)
                } catch (e: Exception) {
                    _cityState.value = CityState.Error(e.message)
                }
                delay(10_000)
                _percentage.value += 20
            }


        }
    }


    fun makeWaitingMsg(waitingMsg: Array<String>) {
        viewModelScope.launch(Dispatchers.IO){
            val n = waitingMsg.size; var i = 0

            while (true){
                if (i == n) i = 0
                _waitingMsg.value = waitingMsg[i++]

                if(percentage.value >= 100f) break
                delay(6_000)
            }

        }
    }

    private suspend fun fetchForecastWithCityName(cityName: String) {
        when (val result = getForecastWithCityName.getForecast(cityName)) {
            is Resource.Success -> {
                _cityState.value = CityState.Success(result.data)
                _cities.value.add(result.data!!)
            }
            is Resource.Error -> {
                _cityState.value = CityState.Error(result.message)
            }
        }
    }


}