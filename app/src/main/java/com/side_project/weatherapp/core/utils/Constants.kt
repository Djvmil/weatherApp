package com.side_project.weatherapp.core.utils

object NetworkService {
    const val BASE_URL: String = "https://api.openweathermap.org"
    const val API_KEY: String = "b19cd7ddea9f9d4afd5fc044515f63c4"
    const val UNITS: String = "metric"
    const val LANG: String = "fr"
    const val FORECAST_END_POINT = "/data/2.5/forecast"
}

enum class CITIES(city: String) {
    RENNES("Rennes"),
    PARIS("Paris"),
    TOKYO("Tokyo"),
    BORDEAUX("Bordeaux"),
    LYON("Lyon")
}

object Database {
    const val forecast_table = "forecast_data"
    const val database_name = "weather_data.db"
    const val city_table = "city_data"
    const val my_city_table = "my_city"
}

object Constants {
    const val UNKNOWN_ERROR = "Une erreur inconnue s\'est produite."
    const val UNKNOWN_HOST = "Impossible de résoudre le nom d\\'hôte \"api.openweathermap.org\" : Aucune adresse associée au nom d'hôte"
}

object ExceptionTitles {
    const val GPS_DISABLED = "GPS désactivé"
    const val NO_PERMISSION = "Pas d\'autorisation"
    const val NO_INTERNET_CONNECTION = "Pas de connexion Internet"
    const val UNKNOWN_ERROR = "Unknown Error"
}

object ExceptionDescriptions {
    const val GPS_DISABLED_DESCR = "Votre GPS semble être désactivé, veuillez l\'activer."
    const val NO_PERMISSION_DESCR = "Autoriser sinon le suivi de la localisation ne fonctionnera pas."
    const val NO_INTERNET_CONNECTION_DESCR = "Veuillez vérifier votre connexion internet."
    const val UNKNOWN_ERROR_DESCR = "Un problème s\'est produit."
}

object ErrorCardConsts {
    const val BUTTON_TEXT = "OK"
}

object WeatherConditions {
    const val CLEAR_SKY = "ciel dégagé"
    const val FEW_CLOUDS = "peu nuageux"
    const val SCATTERED_CLOUDS = "partiellement nuageux"
    const val BROKEN_CLOUDS = "nuageux"
    const val SHOWER_RAIN = "shower rain"
    const val RAIN = "rain"
    const val LIGHT_RAIN = "légère pluie"
    const val THUNDERSTORM = "thunderstorm"
    const val SNOW = "snow"
    const val MIST = "mist"
}

object MainWeatherConditions {
    const val CLOUDS = "Clouds"
    const val SNOW = "Snow"
    const val RAIN = "Rain"
    const val THUNDERSTORM = "Thunderstorm"
    const val CLEAR = "Clear"
}