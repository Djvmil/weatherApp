package com.side_project.weatherapp.ui.search

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.side_project.weatherapp.R
import com.side_project.weatherapp.core.designsystem.theme.Blue
import com.side_project.weatherapp.core.designsystem.theme.DarkBlue
import com.side_project.weatherapp.core.designsystem.theme.LightBlue
import com.side_project.weatherapp.core.utils.Constants
import com.side_project.weatherapp.core.utils.ErrorCardConsts
import com.side_project.weatherapp.domain.model.Forecast
import com.side_project.weatherapp.ui.component.ErrorCard
import com.side_project.weatherapp.ui.component.ForecastLazyRow
import com.side_project.weatherapp.ui.component.ForecastTitle
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MoreCityScreen(viewModel: MoreCityViewModel, onNavigateToHomeScreen: () -> Unit) {
    val waitingMsg = stringArrayResource(id = R.array.waiting_msg)
    var launchedRequest by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(DarkBlue, Blue, LightBlue))),
        topBar = { TopBarSection(onNavigateToHomeScreen) },
        backgroundColor = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        ) {
            MoreCityScreenContent(
                viewModel = viewModel
            ) {
                launchedRequest = !launchedRequest
            }

            LaunchedEffect(launchedRequest) {
                viewModel.makeRequests()
                viewModel.makeWaitingMsg(waitingMsg)

            }

        }
    }
}


@Composable
private fun MoreCityScreenContent(
    viewModel: MoreCityViewModel,
    onClickLaunch: () -> Unit,
) {

    WeatherSection(viewModel)
    GradientProgressbar(viewModel.percentage, onClickLaunch)
}

@Composable
fun WeatherSection(viewModel: MoreCityViewModel) {

    if(viewModel.percentage.collectAsState().value >= 100f){
        WeatherCities(viewModel.cities)
    }else{
        WaitingSection(viewModel)

    }
}

@Composable
fun WaitingSection(viewModel: MoreCityViewModel) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp)
            .height(LocalConfiguration.current.screenHeightDp.dp / 2)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = LocalConfiguration.current.screenHeightDp.dp / 4),
            text = viewModel.waitingMsg.collectAsState().value,
            style = MaterialTheme.typography.h3.copy(fontSize = 18.sp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun WeatherCities(cities: StateFlow<MutableList<Forecast>>) {
    val citiesValue = cities.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(LocalConfiguration.current.screenHeightDp.dp / 2)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ForecastTitle(text = stringResource(id = R.string.weather_city_title))
        ForecastLazyRow(forecasts = citiesValue)
    }
}

@Composable
private fun TopBarSection(onBackClick: () -> Unit) {
    TopAppBar(
        modifier = Modifier.statusBarsPadding(),
        title = { Text(text = stringResource(id = R.string.topbar_title), style = MaterialTheme.typography.h2) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )
}

@Composable
private fun CityListErrorMessage(errorMessage: String?) {
    ErrorCard(
        errorTitle = errorMessage ?: Constants.UNKNOWN_ERROR,
        errorDescription = errorMessage ?: Constants.UNKNOWN_ERROR,
        errorButtonText = ErrorCardConsts.BUTTON_TEXT,
        onClick = {},
        cardModifier = Modifier
            .fillMaxWidth()
            .height(LocalConfiguration.current.screenHeightDp.dp / 4 + 48.dp)
    )
}


@Composable
fun GradientProgressbar(percentage: StateFlow<Float>, onClickLaunch: () -> Unit) {

    val downloadedPercentage =  percentage.collectAsState().value
    val gradientColors: List<Color> = listOf(
        Color(0xFF6ce0c4),
        Color(0xFF40c7e7),
        Color(0xFF6ce0c4),
        Color(0xFF40c7e7)
    )

    val animateNumber = animateFloatAsState(
        targetValue = downloadedPercentage,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 0
        ), label = ""
    )

    Box(
        modifier = Modifier.padding(start = 50.dp, end = 15.dp),
        contentAlignment = Alignment.BottomCenter
    ){
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clickable {
                    if (downloadedPercentage.toInt() >= 100)
                        onClickLaunch.invoke()
                }
        ) {

            var width = size.width - 100

            drawLine(
                color = Color.LightGray.copy(alpha = 0.3f),
                cap = StrokeCap.Round,
                strokeWidth = size.height,
                start = Offset(x = 0f, y = 0f),
                end = Offset(x = width, y = 0f)
            )

            val progress = (animateNumber.value / 100) *  width

            drawLine(
                brush = Brush.linearGradient(
                    colors = gradientColors
                ),
                cap = StrokeCap.Round,
                strokeWidth = size.height,
                start = Offset(x = 0f, y = 0f),
                end = Offset(x = progress, y = 0f)
            )

        }

        Text(
            modifier = Modifier
                .padding(bottom = 40.dp, end = 10.dp),
            text = if (downloadedPercentage.toInt() == 100 ) "Recommencer" else downloadedPercentage.toInt().toString() + "%",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.sourcesansoro_semibold, FontWeight.Bold)),
                fontSize = 32.sp
            )
        )
    }

}

