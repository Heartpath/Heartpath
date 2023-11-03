package com.zootopia.presentation.util

/**
 * 거리값 포맷
 */
fun distanceIntToString(dist: Int): String {
    return if (dist >= 1000) {
        "${String.format("%.2f", dist.toFloat() / 1000)} km"
    } else {
        "$dist m"
    }
}

/**
 * 소요 시간 포맷
 */
fun timeIntToString(time: Int): String {
    var str = "${(time / 60) % 60} 분"
    if (time >= 3600) str = "${time / 3600} 시간 " + str
    return str
}