package com.side_project.weatherapp.data.mapper

import com.side_project.weatherapp.data.datasource.remote.api.entity.CityDto
import com.side_project.weatherapp.data.datasource.remote.api.entity.CoordDto
import com.side_project.weatherapp.domain.mapper.IEntityMapper
import com.side_project.weatherapp.domain.model.City
import com.side_project.weatherapp.domain.model.Coord
import javax.inject.Inject

class CityDtoMapper @Inject constructor() : IEntityMapper<CityDto, City> {
    override fun mapFromEntity(entity: CityDto): City {
        return City(
            entity.country,
            entity.timezone,
            entity.sunrise,
            entity.sunset,
            entity.cityName,
            Coord(
                entity.coordinate.latitude,
                entity.coordinate.longitude
            )
        )
    }

    override fun entityFromModel(model: City): CityDto {
        return CityDto(
            model.country,
            model.timezone,
            model.sunrise,
            model.sunset,
            model.cityName,
            CoordDto(
                model.coordinate.latitude,
                model.coordinate.longitude
            )
        )
    }

}