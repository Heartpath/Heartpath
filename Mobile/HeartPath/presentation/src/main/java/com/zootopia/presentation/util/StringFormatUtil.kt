package com.zootopia.presentation.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

/**
 * 숫자 단위 000으로 , 나눠 주는 함수
 */
fun makeComma(num: Int): String {
    var comma = DecimalFormat("#,###")
    return "${comma.format(num)}"
}

/**
 * 날짜 시간 포멧팅
 */
@RequiresApi(Build.VERSION_CODES.O)
fun convertDateFormat(inputDateString: String): String {
    // 입력된 문자열을 LocalDateTime으로 파싱
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val inputDateTime = LocalDateTime.parse(inputDateString, inputFormatter)
    
    // 새로운 형식으로 변환
    val outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    return inputDateTime.format(outputFormatter)
}