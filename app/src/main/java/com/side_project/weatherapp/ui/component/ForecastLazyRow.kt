package com.side_project.weatherapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.side_project.weatherapp.core.helpers.HourConverter
import com.side_project.weatherapp.core.utils.WeatherType
import com.side_project.weatherapp.domain.model.Forecast

@Composable
fun ForecastLazyRow(forecasts: List<Forecast>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(forecasts) {
            WeatherCard(
                city = it.cityDtoData.cityName,
                country = "(${it.cityDtoData.country})",
                weatherIcon = WeatherType.setWeatherType(
                    it.weatherList[0].weatherStatus[0].mainDescription,
                    it.weatherList[0].weatherStatus[0].description,
                    HourConverter.convertHour(it.weatherList[0].date.substring(11, 13)),
                ),
                degree = "${it.weatherList[0].weatherData.temp.toInt()}°"
            )
        }
    }
}

@Composable
private fun WeatherCard(city: String, country: String, weatherIcon: Int, degree: String) {
    Card(
        modifier = Modifier,
        shape = MaterialTheme.shapes.large,
        backgroundColor = MaterialTheme.colors.secondary
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 3.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = city, style = MaterialTheme.typography.h3.copy(fontSize = 12.sp))
                Text(text = country, style = MaterialTheme.typography.h3.copy(fontSize = 14.sp))
            }
            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = weatherIcon),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Text(text = degree, style = MaterialTheme.typography.h3.copy(fontSize = 24.sp))
        }
    }
}