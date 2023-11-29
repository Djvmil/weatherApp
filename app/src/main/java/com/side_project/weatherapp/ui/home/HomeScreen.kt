package com.side_project.weatherapp.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.side_project.weatherapp.R
import com.side_project.weatherapp.core.helpers.SetError
import com.side_project.weatherapp.core.utils.ErrorCardConsts
import com.side_project.weatherapp.core.utils.ExceptionTitles
import com.side_project.weatherapp.domain.model.Forecast
import com.side_project.weatherapp.ui.component.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(viewModel: HomeViewModel, onNavigateToSearchCityScreen: () -> Unit) {
    val homeCurrentWeatherState by viewModel.homeForecastState.collectAsState()
    val activity = (LocalContext.current as? Activity)

    Scaffold(modifier = Modifier.fillMaxSize()) {
        BackgroundImage()
        WeatherSection(homeCurrentWeatherState, onNavigateToSearchCityScreen) { activity?.finish() }
    }
}

@Composable
private fun BackgroundImage() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun WeatherSection(currentWeatherState: HomeForecastState, onNavigateToSearchCityScreen: () -> Unit, errorCardOnClick: () -> Unit) {
    when (currentWeatherState) {
        is HomeForecastState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressBar(modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp / 3))
            }
        }
        is HomeForecastState.Success -> {
            if (currentWeatherState.forecast != null) {
                CurrentWeatherSection(currentWeatherState.forecast)

                ButtonMoreCity(onNavigateToSearchCityScreen)
            }
        }
        is HomeForecastState.Error -> {
            ErrorCard(
                modifier = Modifier.fillMaxSize(),
                errorTitle = currentWeatherState.errorMessage ?: ExceptionTitles.UNKNOWN_ERROR,
                errorDescription = SetError.setErrorCard(
                    currentWeatherState.errorMessage ?: ExceptionTitles.UNKNOWN_ERROR
                ),
                errorButtonText = ErrorCardConsts.BUTTON_TEXT,
                errorCardOnClick,
                cardModifier = Modifier
                    .fillMaxWidth()
                    .height(LocalConfiguration.current.screenHeightDp.dp / 4 + 48.dp)
                    .padding(horizontal = 64.dp)
            )
        }
    }
}

@Composable
fun ButtonMoreCity(onNavigateToMoreCityScreen: () -> Unit) {
   Box(
       modifier = Modifier
           .fillMaxSize()
           .padding(bottom = 120.dp),
       Alignment.BottomCenter,
   ){
       Button(modifier = Modifier
           .fillMaxWidth()
           .padding(15.dp)
           .height(70.dp),
           onClick = {onNavigateToMoreCityScreen() }) {
           Image(
               painterResource(id = R.drawable.few_clouds_night),
               contentDescription ="More City",
               modifier = Modifier.size(20.dp))

           Text(text = "Voir Plus de Ville", Modifier.padding(start = 10.dp))
       }
   }
}



@Composable
private fun CurrentWeatherSection(todayWeather: Forecast) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 72.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = todayWeather.cityDtoData.cityName,
            style = MaterialTheme.typography.h2
        )
        Text(
            text = "${todayWeather.weatherList[0].weatherData.temp.toInt()}${stringResource(id = R.string.degree)}",
            style = MaterialTheme.typography.h1
        )
        Text(
            text = todayWeather.weatherList[0].weatherStatus[0].description,
            style = MaterialTheme.typography.h3,
            color = Color.Gray
        )
    }
}
