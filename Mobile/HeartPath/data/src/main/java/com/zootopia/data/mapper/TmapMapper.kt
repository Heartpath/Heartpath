package com.zootopia.data.mapper

import com.zootopia.data.model.map.request.TmapWalkRoadRequest
import com.zootopia.data.model.map.response.tmap.FeatureCollectionResponse
import com.zootopia.data.model.map.response.tmap.FeatureResponse
import com.zootopia.data.model.map.response.tmap.GeometryResponse
import com.zootopia.data.model.map.response.tmap.PropertiesResponse
import com.zootopia.domain.model.tmap.FeatureCollectionDto
import com.zootopia.domain.model.tmap.FeatureDto
import com.zootopia.domain.model.tmap.GeometryDto
import com.zootopia.domain.model.tmap.PropertiesDto
import com.zootopia.domain.model.tmap.RequestTmapWalkRoadDto

private const val TAG = "TmapMapper_HP"
fun RequestTmapWalkRoadDto.toData(): TmapWalkRoadRequest {
    return TmapWalkRoadRequest(
        startX = startX,
        startY = startY,
        endX = endX,
        endY = endY,
        reqCoordType = reqCoordType,
        resCoordType = resCoordType,
        startName = startName,
        endName =endName,
    )
}

fun FeatureCollectionResponse.toDomain(): FeatureCollectionDto {
    return FeatureCollectionDto(
        type = type,
        features = features.map { it.toDomain() }.toMutableList()
    )
}

fun FeatureResponse.toDomain(): FeatureDto {
    return FeatureDto(
        type = type,
        geometry = geometry.toDomain(),
        properties = properties.toDomain(),
    )
}

fun GeometryResponse.toDomain(): GeometryDto {
    return GeometryDto(
        type = type,
        coordinates =
        if(coordinates.isNullOrEmpty()) {
            listOf()
        } else if (coordinates[0] is List<*>) {
            // coordinates가 List<List<Double>> 형식인 경우
            coordinates as List<List<Double>>
        } else {
            // coordinates가 List<Double> 형식인 경우, 이를 List<List<Double>>로 변환
            listOf(coordinates as List<Double>)
        }
    )
}

fun PropertiesResponse.toDomain(): PropertiesDto {
    return PropertiesDto(
        name = name,
        description = description,
    )
}