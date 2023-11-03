package com.zootopia.data.mapper

import com.zootopia.data.model.map.response.navermap.GoalResponse
import com.zootopia.data.model.map.response.navermap.GuideResponse
import com.zootopia.data.model.map.response.navermap.LocationResponse
import com.zootopia.data.model.map.response.navermap.MapDirectionResponse
import com.zootopia.data.model.map.response.navermap.RouteResponse
import com.zootopia.data.model.map.response.navermap.SectionResponse
import com.zootopia.data.model.map.response.navermap.SummaryResponse
import com.zootopia.data.model.map.response.navermap.TrafastResponse
import com.zootopia.domain.model.navermap.GoalDto
import com.zootopia.domain.model.navermap.GuideDto
import com.zootopia.domain.model.navermap.LocationDto
import com.zootopia.domain.model.navermap.MapDirectionDto
import com.zootopia.domain.model.navermap.RouteDto
import com.zootopia.domain.model.navermap.SectionDto
import com.zootopia.domain.model.navermap.SummaryDto
import com.zootopia.domain.model.navermap.TrafastDto



fun MapDirectionResponse.toDomain(): MapDirectionDto {
    return MapDirectionDto(
        code = code,
        message = message,
        currentDateTime = currentDateTime,
        route = route.toDomain(),
    )
}

fun RouteResponse.toDomain(): RouteDto {
    return RouteDto(
        trafast = trafast.map { it.toDomain() }.toMutableList(),
    )
}

fun TrafastResponse.toDomain(): TrafastDto {
    return TrafastDto(
        summary = summary.toDomain(),
        path = path,
        section = section.map { it.toDomain() }.toMutableList(),
        guide = guide.map { it.toDomain() }.toMutableList(),
    )
}

fun SummaryResponse.toDomain(): SummaryDto {
    return SummaryDto(
        start = start.toDomain(),
        goal = goal.toDomain(),
        distance = distance,
        duration = duration,
        bbox = bbox,
        tollFare = tollFare,
        taxiFare = taxiFare,
        fuelPrice = fuelPrice,
    )
}

fun LocationResponse.toDomain(): LocationDto {
    return LocationDto(
        location = location
    )
}

fun SectionResponse.toDomain(): SectionDto {
    return SectionDto(
        pointIndex = pointIndex,
        pointCount = pointCount,
        distance = distance,
        name = name,
        congestion = congestion,
        speed = speed
    )
}

fun GoalResponse.toDomain(): GoalDto {
    return GoalDto(
        location = location,
        dir = dir,
    )
}

fun GuideResponse.toDomain(): GuideDto {
    return GuideDto(
        pointIndex = pointIndex,
        type = type,
        instructions = instructions,
        distance = distance,
        duration = duration,
    )
}


