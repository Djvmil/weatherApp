package com.side_project.weatherapp.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.side_project.weatherapp.ui.home.HomeScreen
import com.side_project.weatherapp.ui.home.HomeViewModel
import com.side_project.weatherapp.ui.search.MoreCityScreen
import com.side_project.weatherapp.ui.search.MoreCityViewModel

@Composable
fun NavGraph(
    startDestination: String = NavScreen.HomeScreen.route,
    homeViewModel: HomeViewModel,
    moreCityViewModel: MoreCityViewModel
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = startDestination
        ) {
            composable(NavScreen.HomeScreen.route) {
                HomeScreen(homeViewModel) { navController.navigate(NavScreen.SearchCityScreen.route) }
            }
            composable(NavScreen.SearchCityScreen.route) {
                 MoreCityScreen(moreCityViewModel) {
                    navController.navigate(NavScreen.HomeScreen.route) {
                        launchSingleTop = true
                        popUpTo(NavScreen.HomeScreen.route)
                    }
                }
            }
        }
    }
}